package com.bfi.rdv.services;

import com.bfi.rdv.entities.RendezVous;
import com.bfi.rdv.repositories.RendezVousRepository;
import com.bfi.rdv.services.interfaces.IRendezVousService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequestMapping("/rdv")
@RequiredArgsConstructor
public class RendezVousService implements IRendezVousService {

    @Autowired
    private final RendezVousRepository rendezVousRepository;

    @Override
    public RendezVous saveRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public RendezVous updateRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public void deleteRendezVous(Long id) {
        rendezVousRepository.deleteById(id);
    }

    @Override
    public RendezVous getRendezVousById(Long id) {
        return rendezVousRepository.findById(id).orElse(null);
    }

    @Override
    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }


    @Override
    public List<RendezVous> findAllRendezVousByUser(Long idUser) {
        return rendezVousRepository.findAllByIdUser(idUser);
    }
}

