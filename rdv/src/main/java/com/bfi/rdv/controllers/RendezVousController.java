package com.bfi.rdv.controllers;

import com.bfi.rdv.entities.RendezVous;
import com.bfi.rdv.services.interfaces.IRendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rdv")
public class RendezVousController {

    @Autowired
    private IRendezVousService rendezVousService;

    @PostMapping
    public RendezVous createRendezVous(@RequestBody RendezVous rendezVous) {
        return rendezVousService.saveRendezVous(rendezVous);
    }

    @PutMapping("/{id}")
    public RendezVous updateRendezVous(@PathVariable Long id, @RequestBody RendezVous rendezVous) {
        return rendezVousService.updateRendezVous(rendezVous);
    }

    @DeleteMapping("/{id}")
    public void deleteRendezVous(@PathVariable Long id) {
        rendezVousService.deleteRendezVous(id);
    }

    @GetMapping("/{id}")
    public RendezVous getRendezVousById(@PathVariable Long id) {
        return rendezVousService.getRendezVousById(id);
    }

    @GetMapping
    public List<RendezVous> getAllRendezVous() {
        return rendezVousService.getAllRendezVous();
    }


    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<RendezVous>> findAllRdvs(
            @PathVariable("idUser") Long idUser
    ) {
        return ResponseEntity.ok(rendezVousService.findAllRendezVousByUser(idUser));
    }
}
