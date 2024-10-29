package com.bfi.credit.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "doc_attaches")
public class DocAttache implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long idD;
    Integer numD;
    private boolean obligatoire;
    private String nomFichier;
    private String typeMime;
    private Long taille;
    private byte[] data;

    @ManyToOne
    Credit credit;

}