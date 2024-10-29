package com.bfi.authentification.entities;

import com.bfi.authentification.entities.enums.EtatC;
import com.bfi.authentification.entities.enums.TCompte;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(exclude = "user")
public class CompteBancaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompte;
    private String numeroCompte;
    private float solde;
    private Date dateOuvCompte;
    @Enumerated(EnumType.STRING)
    private TCompte typeCompte;
    private float montantRouge;
    private Integer nombreDeRetardDePaiement;
    @Enumerated(EnumType.STRING)
    private EtatC etatDeCompte;
    @ManyToOne
    @JsonIgnoreProperties("compteBancaires")
    private User user;
}
