package com.bfi.credit.services.interfaces;

import com.bfi.credit.entities.ContratCredit;
import com.bfi.credit.entities.Echeance;
import com.bfi.credit.entities.enums.TypeUnite;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IContratCreditService {
    List<ContratCredit> getAllContratCredits();

    ContratCredit updateContratCredit(ContratCredit contratCredit);

    Boolean deleteContratCredit(Long idDoss);

    ContratCredit getContratCreditById(Long idD);


    Set<Echeance> genererEcheances(float montant, int duree, float interet, TypeUnite unite, Long idC);
}
