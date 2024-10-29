package com.bfi.rdv.services.interfaces;

import com.bfi.rdv.entities.RendezVous;

import java.util.List;

public interface IRendezVousService {
    RendezVous saveRendezVous(RendezVous rendezVous);
    RendezVous updateRendezVous(RendezVous rendezVous);
    void deleteRendezVous(Long id);
    RendezVous getRendezVousById(Long id);
    List<RendezVous> getAllRendezVous();
     List<RendezVous> findAllRendezVousByUser(Long idU);
}
