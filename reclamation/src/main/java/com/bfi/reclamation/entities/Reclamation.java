package com.bfi.reclamation.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Reclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRec;
    private String sujet;
    private String description;
    private Date dateSoumission;
    @Enumerated(EnumType.STRING)
    private StatRec status;

    private Long idUser;

}
