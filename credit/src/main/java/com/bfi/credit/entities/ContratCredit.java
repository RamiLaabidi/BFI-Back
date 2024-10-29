package com.bfi.credit.entities;

import com.bfi.credit.entities.enums.TypeCredit;
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
public class ContratCredit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idC;
    String signature;
    String condition;
    Date dateC;
    @OneToOne
    Credit credit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="contratCredit")
    private Set<Echeance> echeances;
}
