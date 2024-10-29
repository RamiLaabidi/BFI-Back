package com.bfi.credit.controllers;
import com.bfi.credit.entities.Echeance;
import com.bfi.credit.entities.ContratCredit;
import com.bfi.credit.entities.enums.TypeUnite;
import com.bfi.credit.services.interfaces.IContratCreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/contrat")
@RequiredArgsConstructor
public class ContratCreditController {

    @Autowired
    private final IContratCreditService contratCreditService;




    @PutMapping("/update")
    public ContratCredit updateContratCredit(@RequestBody ContratCredit contratCredit) {

        return contratCreditService.updateContratCredit(contratCredit);
    }


    @DeleteMapping("/delete/{idC}")
    public ResponseEntity<String> deleteContratCredit(@PathVariable("idC") Long idC) {
        boolean deleted = contratCreditService.deleteContratCredit(idC);
        if (deleted) {
            return ResponseEntity.ok("ContratCredit deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ContratCredit with ID " + idC + " does not exist.");
        }
    }

    @GetMapping("/get/{idC}")
    public ContratCredit getContratCreditById(@PathVariable("idC") Long idC) {
        return contratCreditService.getContratCreditById(idC);
    }


    @GetMapping("/getAllContratCredit")
    public List<ContratCredit> getAllContratCredit() {
        return ResponseEntity.ok().body(contratCreditService.getAllContratCredits()).getBody();

    }





    @PostMapping("/{idC}/generer-echeances")
    public ResponseEntity<Set<Echeance>> genererEtAssignerEcheances(
            @PathVariable Long idC,
            @RequestParam float montant,
            @RequestParam int duree,
            @RequestParam float interet,
            @RequestParam TypeUnite unite) {

        Set<Echeance> echeances = contratCreditService.genererEcheances(montant, duree, interet, unite, idC);

        return ResponseEntity.ok().body(echeances);
    }
}
