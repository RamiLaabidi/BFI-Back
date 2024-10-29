package com.bfi.credit.services.interfaces;

import com.bfi.credit.entities.Echeance;

import java.util.List;

public interface IEcheanceService {
    Echeance saveEcheance(Echeance echeance);
    Echeance updateEcheance(Echeance echeance);
    void deleteEcheance(Long id);
    Echeance getEcheanceById(Long id);
    List<Echeance> getAllEcheances();
    List<Echeance> genererEcheances(float montant, int dureeCredit, String typeCredit);

}
