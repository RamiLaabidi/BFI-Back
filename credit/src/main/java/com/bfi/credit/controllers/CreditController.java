package com.bfi.credit.controllers;

import com.bfi.credit.entities.Credit;
import com.bfi.credit.entities.Echeance;
import com.bfi.credit.entities.enums.StatutCredit;
import com.bfi.credit.entities.enums.TypeUnite;
import com.bfi.credit.services.interfaces.ICreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    private ICreditService creditService;

    @GetMapping
    public List<Credit> getCredit() {
        return creditService.getAllCredit();
    }
    @GetMapping("/{id}")
    public Credit getCreditById(@PathVariable("id") Long id) {
        return creditService.getCreditById(id);
    }


    @PostMapping
    public Credit createDemande(@RequestBody Credit DC) {
        return creditService.createCredit(DC);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Credit>> findAllCredit(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(creditService.findAllCreditByUser(userId));
    }

    @GetMapping("/simulate")
    public Float simulate(
            @RequestParam Float montant,
            @RequestParam Integer duree,
            @RequestParam Float interet,
            @RequestParam TypeUnite unite) {
        return creditService.simulateur(montant, duree, interet, unite);
    }


    @PostMapping("/user/{idU}")
    public ResponseEntity<Credit> createCredit(@PathVariable("idU") Long idU, @RequestBody Credit Credit) {
        Credit createdCredit = creditService.addCredit(idU, Credit);
        return ResponseEntity.ok(createdCredit);
    }

    @GetMapping("/nbreDemandes")
    public ResponseEntity<Integer> getNbreDemandes() {
        Integer nbreDemandes = creditService.calculateNbreDemandes();
        return ResponseEntity.ok(nbreDemandes);
    }

    @GetMapping("/nbreDemandesAcc")
    public ResponseEntity<Integer> getNbreDemandesAcc() {
        Integer nbreDemandesAcc = creditService.calculateNbreDemandesAcceptes();
        return ResponseEntity.ok(nbreDemandesAcc);
    }

    @GetMapping("/nbreDemandesRej")
    public ResponseEntity<Integer> getNbreDemandesRej() {
        Integer nbreDemandesRej = creditService.calculateNbreDemandesRefusee();
        return ResponseEntity.ok(nbreDemandesRej);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Credit> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> status) {
        Credit updatedCredit = creditService.updateStatus(id, StatutCredit.valueOf(status.get("statut")));
        return ResponseEntity.ok(updatedCredit);
    }


    @PostMapping("/add")
    public ResponseEntity<Credit> createCredit(@RequestBody Credit credit) {
        System.out.println("Received Credit: " + credit);
        System.out.println("Date Received: " + credit.getDateDemande());

        try {
            Credit savedCredit = creditService.saveCredit(credit);
            return ResponseEntity.ok(savedCredit);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<Set<Echeance>> generateEcheances(
            @RequestParam float montant,
            @RequestParam int duree,
            @RequestParam String typeCredit) {

        Set<Echeance> echeances = creditService.genererEcheances(montant, duree, typeCredit);
        return ResponseEntity.ok(echeances);


    }
}
