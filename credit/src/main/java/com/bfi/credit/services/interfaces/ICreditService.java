package com.bfi.credit.services.interfaces;

import com.bfi.credit.entities.Credit;
import com.bfi.credit.entities.Echeance;
import com.bfi.credit.entities.enums.StatutCredit;
import com.bfi.credit.entities.enums.TypeUnite;

import java.util.List;
import java.util.Set;

public interface ICreditService {
     Float simulateur(Float montant, Integer duree, Float interet, TypeUnite unite);
     Integer calculateNbreDemandes();
     Credit updateStatus(Long id, StatutCredit statutCredit) ;
     Integer calculateNbreDemandesRefusee();
     List<Credit> getAllCredit();
     Credit createCredit(Credit credit);
     List<Credit> findAllCreditByUser(Long userId);
    public Credit addCredit(Long idU, Credit credit) ;
     public Integer calculateNbreDemandesAcceptes();
    public Credit saveCredit(Credit credit);
    public Set<Echeance> genererEcheances(float montant, int duree, String typeCredit);
    public Credit getCreditById(Long id);
}
