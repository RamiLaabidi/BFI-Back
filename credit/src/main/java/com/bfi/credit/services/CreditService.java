package com.bfi.credit.services;

import com.bfi.credit.entities.Credit;
import com.bfi.credit.entities.Echeance;
import com.bfi.credit.entities.enums.StatutCredit;
import com.bfi.credit.entities.enums.TypeUnite;
import com.bfi.credit.repositories.CreditRepository;
import com.bfi.credit.services.interfaces.ICreditService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequestMapping("/credit")
@RequiredArgsConstructor
public class CreditService implements ICreditService {

    @Autowired
    private CreditRepository creditRepository;

    private RestTemplate restTemplate;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 10; // Change the length as needed

    public  String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public List<Credit> getAllCredit(){
        return creditRepository.findAll();
    }

    public Credit createCredit(Credit credit){
       // credit.setDateDemande(new Date());
        credit.setNumD(generateRandomString()); // Set the random string

        return creditRepository.save(credit);
    }
    @Override
    public Credit saveCredit(Credit credit) {
        // Set the interest rate based on the type of credit
        switch (credit.getTypeCredit()) {
            case VOITURE:
                credit.setInteret(5.8f);
                break;
            case PERSONNEL:
                credit.setInteret(6.0f);
                break;
            case IMMOBILIER:
                credit.setInteret(4.9f);
                break;
            case RENOVATION:
                credit.setInteret(5.9f);
                break;
            default:
                throw new IllegalArgumentException("Invalid type of credit");

        }
        credit.setNumD(generateRandomString()); // Set the random string

        // Save the credit to the database
        return creditRepository.save(credit);
    }
    public List<Credit> findAllCreditByUser(Long userId) {
        return creditRepository.findAllByIdUser(userId);
    }
    public Credit addCredit(Long idU, Credit credit) {
        // Appel au service d'authentification pour vérifier si l'utilisateur existe
        String userServiceUrl = "http://localhost:8077/auth/login/" + idU;
        User user = restTemplate.getForObject(userServiceUrl, User.class);

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        // Assigner l'ID de l'utilisateur à la demande de crédit
        credit.setIdUser(idU);

        // Sauvegarder la demande de crédit dans la base de données
        return creditRepository.save(credit);
    }

    public Float simulateur(Float montant, Integer duree, Float interet, TypeUnite unite) {
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
        // Calcul du montant total à payer en tenant compte de l'intérêt
        float montantTotal = montant * (1 + interet * duree);

        // Montant à payer par échéance
        return montantTotal / duree;
    }
    public Integer calculateNbreDemandes() {
        List<Credit> credits = creditRepository.findAll();
        int nbreDemandes = 0;
        for (Credit credit : credits) {
            nbreDemandes++;
        }
        return nbreDemandes;
    }
    public Credit updateStatus(Long id, StatutCredit statutCredit) {
        Optional<Credit> CreditOptional = creditRepository.findById(id);
        if (CreditOptional.isPresent()) {
            Credit Credit = CreditOptional.get();
            Credit.setStatutCredit(statutCredit);
            return creditRepository.save(Credit);
        } else {
            throw new RuntimeException("Credit not found with id " + id);
        }
    }
    public Integer calculateNbreDemandesAcceptes() {
        List<Credit> credits = creditRepository.findAll();
        int nbreDemandes = 0;
        StatutCredit acceptedStatus = StatutCredit.ACCEPTÉE;

        for (Credit Credit : credits) {
            if (Credit.getStatutCredit() == acceptedStatus) {
                nbreDemandes++;
            }
        }
        return nbreDemandes;
    }
    public Integer calculateNbreDemandesRefusee() {
        List<Credit> credits = creditRepository.findAll();
        int nbreDemandes = 0;
        StatutCredit rejectedStatus = StatutCredit.REFUSÉE;

        for (Credit Credit : credits) {
            if (Credit.getStatutCredit() == rejectedStatus) {
                nbreDemandes++;
            }
        }
        return nbreDemandes;
    }

    @Override
    public Set<Echeance> genererEcheances(float montant, int duree, String typeCredit) {
        // Map typeCredit to interet
        float interet;
        switch (typeCredit) {
            case "VOITURE":
                interet = 0.058f;
                break;
            case "PERSONNEL":
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
        float mensualite = (montant * interet) / (1 - (float) Math.pow(1 + interet, -duree));

        Calendar calendar = Calendar.getInstance();

        // Création des échéances
        Set<Echeance> nouvellesEcheances = new HashSet<>();
        float capitalRestant = montant;

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

            nouvellesEcheances.add(echeance);

            // Passage à la même date du mois suivant
            calendar.add(Calendar.MONTH, 1);
        }

        return nouvellesEcheances;
    }
    @Override
    public Credit getCreditById(Long id) {
        return creditRepository.findById(id).orElse(null);

    }
}
