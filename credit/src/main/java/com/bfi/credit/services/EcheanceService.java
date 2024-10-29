package com.bfi.credit.services;

import com.bfi.credit.entities.ContratCredit;
import com.bfi.credit.entities.Echeance;
import com.bfi.credit.repositories.ContratCreditRepository;
import com.bfi.credit.repositories.EcheanceRepository;
import com.bfi.credit.services.interfaces.IEcheanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Service
@RequestMapping("/echeance")
@RequiredArgsConstructor
public class EcheanceService implements IEcheanceService {

    @Autowired
    private EcheanceRepository echeanceRepository;
    private ContratCreditRepository contratCreditRepository;


    @Override
    public Echeance saveEcheance(Echeance echeance) {
        return echeanceRepository.save(echeance);
    }

    @Override
    public Echeance updateEcheance(Echeance echeance) {
        return echeanceRepository.save(echeance);
    }

    @Override
    public void deleteEcheance(Long id) {
        echeanceRepository.deleteById(id);
    }

    @Override
    public Echeance getEcheanceById(Long id) {
        return echeanceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Echeance> getAllEcheances() {
        return echeanceRepository.findAll();
    }
    public List<Echeance> genererEcheances(float montant, int dureeCredit, String typeCredit) {
        // Map typeCredit to interet
        float interet;
        switch (typeCredit) {
            case "PERSONNEL":
                interet = 0.058f;
                break;
            case "VOITURE":
                interet = 0.06f;
                break;
            case "IMMOBILIER":
                interet = 0.049f;
                break;
            case "RENOVATION":
                interet = 0.059f;
                break;
            default:
                throw new IllegalArgumentException("Invalid type of credit");
        }

        // Calcul de la mensualité en utilisant la formule de l'annuité
        float mensualite = (montant * interet) / (1 - (float) Math.pow(1 + interet, -dureeCredit));

        Calendar calendar = Calendar.getInstance();

        // Création des échéances
        Set<Echeance> nouvellesEcheances = new HashSet<>();
        float capitalRestant = montant;

        for (int i = 0; i < dureeCredit; i++) {
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

            nouvellesEcheances.add(echeance);

            // Passage à la même date du mois suivant
            calendar.add(Calendar.MONTH, 1);
        }

        // Convert to list and sort by datePaiement
        List<Echeance> sortedEcheances = new ArrayList<>(nouvellesEcheances);
        sortedEcheances.sort(Comparator.comparing(Echeance::getDatePaiement));

        return sortedEcheances;
    }
}

