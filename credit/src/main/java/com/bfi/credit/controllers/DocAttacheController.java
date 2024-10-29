package com.bfi.credit.controllers;

import com.bfi.credit.entities.DocAttache;
import com.bfi.credit.services.interfaces.IDocAttacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docattache")
@RequiredArgsConstructor
public class DocAttacheController {

    @Autowired
    private IDocAttacheService docAttacheService;

    @PostMapping("/add")
    public DocAttache createDocAttache(@RequestBody DocAttache docAttache) {
        return docAttacheService.saveDocAttache(docAttache);
    }

    @PutMapping("/{id}")
    public DocAttache updateDocAttache(@PathVariable Long id, @RequestBody DocAttache docAttache) {
        return docAttacheService.updateDocAttache(docAttache);
    }

    @DeleteMapping("/delete/{idP}")
    public ResponseEntity<String> deletePieceJointe(@PathVariable("idP") Long idP) {
        boolean deleted = docAttacheService.deletePieceJointe(idP);
        if (deleted) {
            return ResponseEntity.ok("PieceJointe deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PieceJointe with ID " + idP + " does not exist.");
        }
    }

    @GetMapping("/get/{idP}")
    public DocAttache getPieceJointeById(@PathVariable("idP") Long idP) {
        return docAttacheService.getPieceJointeById(idP);
    }


    @GetMapping("/getAllPieceJointe")
    public List<DocAttache> getAllPieceJointe() {
        return ResponseEntity.ok().body(docAttacheService.getAllPieceJointe()).getBody();

    }
}
