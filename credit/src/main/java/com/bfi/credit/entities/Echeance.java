package com.bfi.credit.entities;

import com.bfi.credit.entities.enums.StatutCredit;
import com.bfi.credit.entities.enums.TypeCredit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Echeance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idE;
    Date datePaiement;
    Float montantEcheance;
    Float mensualite;
    Float interetsPayes;
    Float capitalRembourse;
    Float capitalRestantDu;

    @ManyToOne
    ContratCredit contratCredit;
}
