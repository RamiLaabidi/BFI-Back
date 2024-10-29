package com.bfi.authentification.DTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDTO {
    private Long idCredit;
    private float montant;
    private Date dateDemande;
    private Integer dureeCredit;
    private Float interet;
    private Long numD;


}