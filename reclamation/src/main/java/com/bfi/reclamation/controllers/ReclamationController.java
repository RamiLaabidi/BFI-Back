package com.bfi.reclamation.controllers;

import com.bfi.reclamation.entities.Reclamation;
import com.bfi.reclamation.services.interfaces.IReclamationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reclamation")
public class ReclamationController {

    @Autowired
    private final IReclamationService reclamationService;

    @PostMapping("/add")
    public Reclamation createReclamation(@RequestBody Reclamation reclamation) {
        return reclamationService.saveReclamation(reclamation);
    }

    @PutMapping("/{id}")
    public Reclamation updateReclamation(@PathVariable Long id, @RequestBody Reclamation reclamation) {
        return reclamationService.updateReclamation(reclamation);
    }

    @DeleteMapping("/{id}")
    public void deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
    }

    @GetMapping("/{id}")
    public Reclamation getReclamationById(@PathVariable Long id) {
        return reclamationService.getReclamationById(id);
    }

    @GetMapping("/getAll")
    public List<Reclamation> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }




    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<Reclamation>> findAllReclamations(
            @PathVariable("idUser") Long idUser
    ) {
        return ResponseEntity.ok(reclamationService.findAllReclamationByUser(idUser));
    }
}
