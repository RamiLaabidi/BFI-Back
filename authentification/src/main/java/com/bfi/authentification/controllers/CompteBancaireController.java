package com.bfi.authentification.controllers;

import com.bfi.authentification.entities.CompteBancaire;
import com.bfi.authentification.services.interfaces.ICompteBancaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
public class CompteBancaireController {

    @Autowired
    private ICompteBancaireService compteBancaireService;

    @PostMapping
    public CompteBancaire createCompteBancaire(@RequestBody CompteBancaire compteBancaire) {
        return compteBancaireService.saveCompteBancaire(compteBancaire);
    }

    @PutMapping("/{id}")
    public CompteBancaire updateCompteBancaire(@PathVariable Long id, @RequestBody CompteBancaire compteBancaire) {
        return compteBancaireService.updateCompteBancaire(compteBancaire);
    }

    @DeleteMapping("/{id}")
    public void deleteCompteBancaire(@PathVariable Long id) {
        compteBancaireService.deleteCompteBancaire(id);
    }

    @GetMapping("/{id}")
    public CompteBancaire getCompteBancaireById(@PathVariable Long id) {
        return compteBancaireService.getCompteBancaireById(id);
    }

    @GetMapping
    public List<CompteBancaire> getAllCompteBancaires() {
        return compteBancaireService.getAllCompteBancaires();
    }
}
