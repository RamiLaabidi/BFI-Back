package com.bfi.authentification.services.interfaces;

import com.bfi.authentification.entities.CompteBancaire;

import java.util.List;

public interface ICompteBancaireService {
    CompteBancaire saveCompteBancaire(CompteBancaire compteBancaire);
    CompteBancaire updateCompteBancaire(CompteBancaire compteBancaire);
    void deleteCompteBancaire(Long id);
    CompteBancaire getCompteBancaireById(Long id);
    List<CompteBancaire> getAllCompteBancaires();
}
