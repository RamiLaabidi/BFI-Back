package com.bfi.credit.entities;

import com.bfi.credit.entities.enums.StatutCredit;
import com.bfi.credit.entities.enums.TypeCredit;
import com.bfi.credit.entities.enums.TypeUnite;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Credit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long idCredit;
    @Enumerated(EnumType.STRING)
    TypeCredit typeCredit;
    String numD;
    float montant;
    //Date dateDemande;
    //@Enumerated(EnumType.STRING)
    //StatutCredit statutCredit;
    Integer dureeCredit;
   // @Enumerated(EnumType.STRING)
    //TypeUnite typeUnite;
    Float interet;
    @Enumerated(EnumType.STRING)
    private TypeUnite unite;
    Long idUser;
    @Column(name = "date_demande", updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dateDemande = LocalDate.now(); // Automatically set to current date
    @Enumerated(EnumType.STRING)
    @Column(name = "statut_credit")
    StatutCredit statutCredit = StatutCredit.EN_COURS; // Automatically set to ENCOURS
    @Enumerated(EnumType.STRING)
    TypeUnite typeUnite = TypeUnite.MENSUELLE;


    @OneToOne
    ContratCredit contratCredit;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="credit")
    private Set<DocAttache> docAttaches;
}