package com.bfi.credit.services;

import com.bfi.credit.entities.ContratCredit;
import com.bfi.credit.entities.Credit;
import com.bfi.credit.entities.Echeance;
import com.bfi.credit.entities.enums.TypeUnite;
import com.bfi.credit.repositories.ContratCreditRepository;
import com.bfi.credit.repositories.CreditRepository;
import com.bfi.credit.repositories.EcheanceRepository;
import com.bfi.credit.services.interfaces.IContratCreditService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

@Service
@RequestMapping("/contrat")
@RequiredArgsConstructor
public class ContratCreditService implements IContratCreditService {

    @Autowired
    private ContratCreditRepository contratCreditRepository;
    @Autowired
    private EcheanceRepository echeanceRepository;
    @Autowired
    private CreditRepository creditRepository;



    @Override
    public ContratCredit updateContratCredit(ContratCredit contratCredit) {
        return contratCreditRepository.save(contratCredit);
    }

    @Override
    public List<ContratCredit> getAllContratCredits() {
        return contratCreditRepository.findAll();
    }
    @Override
    public Set<Echeance> genererEcheances(float montant, int duree, float interet, TypeUnite unite, Long idC) {
        // Calcul du taux d'intérêt annuel, trimestriel et semestriel à partir du taux mensuel
        float interetAnnuelle = (float) (Math.pow(1 + interet, 12) - 1);
        float interetTrimestriel = (float) (Math.pow(1 + interet, 3) - 1);
        float interetSemestrielle = (float) (Math.pow(1 + interet, 6) - 1);

        // Ajustement du taux d'intérêt et du nombre d'échéances en fonction de l'unité
        switch (unite) {
            case MENSUELLE:
                // Le taux d'intérêt est déjà mensuel, aucune conversion nécessaire
                break;
            case TRIMESTRIELLE:
                interet = interetTrimestriel;
                duree /= 3; // Convertir le nombre d'échéances en trimestrielles
                break;
            case SEMESTERIELLE:
                interet = interetSemestrielle;
                duree /= 6; // Convertir le nombre d'échéances en semestrielles
                break;
            case ANNUELLE:
                interet = interetAnnuelle;
                duree /= 12; // Convertir le nombre d'échéances en annuelles
                break;
        }
        // Calcul de la mensualité en utilisant la formule de l'annuité
        float mensualite = (montant * interet) / (1 - (float) Math.pow(1 + interet, -duree));

        Calendar calendar = Calendar.getInstance();

        // Création des échéances
        Set<Echeance> nouvellesEcheances = new HashSet<>();
        float capitalRestant = montant;

        // Récupérer le contrat correspondant
        ContratCredit contratCredit = contratCreditRepository.findById(idC)
                .orElseThrow(() -> new IllegalArgumentException("ContratCredit not found"));

        for (int i = 0; i < duree; i++) {
            Echeance echeance = new Echeance();
            // Définir le dernier jour du mois courant
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            echeance.setDatePaiement(calendar.getTime()); // Date de paiement de l'échéance

            float interetsPayes = capitalRestant * interet; // Intérêts payés pour cette échéance
            float capitalRembourse = mensualite - interetsPayes; // Capital remboursé pour cette échéance

            // Mise à jour du capital restant dû
            capitalRestant -= capitalRembourse;

            echeance.setCapitalRembourse(capitalRembourse); // Capital remboursé
            echeance.setCapitalRestantDu(capitalRestant); // Capital restant dû
            echeance.setInteretsPayes(interetsPayes); // Intérêts payés
            echeance.setMensualite(mensualite); // Mensualité
            echeance.setContratCredit(contratCredit); // Associer l'échéance au contrat

            // Enregistrer l'échéance en base de données
            echeance = echeanceRepository.save(echeance);

            nouvellesEcheances.add(echeance);

            // Passage à la même date du mois suivant
            calendar.add(Calendar.MONTH, 1);
        }
        // Ajouter les nouvelles échéances au contrat
        contratCredit.getEcheances().addAll(nouvellesEcheances);
        contratCreditRepository.save(contratCredit);

        return nouvellesEcheances;
    }



    @Override
    public Boolean deleteContratCredit(Long idC) {
        Optional<ContratCredit> ContratCreditOptional = contratCreditRepository.findById(idC);
        if (ContratCreditOptional.isPresent()) {
            contratCreditRepository.deleteById(idC);
            return true; // La suppression a été effectuée avec succès
        } else {
            return false; // L'identifiant spécifié n'existe pas
        }
    }


    @Override
    public ContratCredit getContratCreditById(Long idDoss) {
        return contratCreditRepository.findById(idDoss).orElse(null);

    }

}
