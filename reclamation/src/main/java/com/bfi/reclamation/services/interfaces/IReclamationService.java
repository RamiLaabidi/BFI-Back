package com.bfi.reclamation.services.interfaces;

import com.bfi.reclamation.entities.Reclamation;

import java.util.List;

public interface IReclamationService {
    Reclamation saveReclamation(Reclamation reclamation);
    Reclamation updateReclamation(Reclamation reclamation);
    void deleteReclamation(Long id);
    Reclamation getReclamationById(Long id);
    List<Reclamation> getAllReclamations();
    List<Reclamation> findAllReclamationByUser(Long idUser);
}
