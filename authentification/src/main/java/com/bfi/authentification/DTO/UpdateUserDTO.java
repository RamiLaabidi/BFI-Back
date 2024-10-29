package com.bfi.authentification.DTO;

import com.bfi.authentification.entities.enums.LIEU_NAISSANCE;
import com.bfi.authentification.entities.enums.SEXE;
import com.bfi.authentification.entities.enums.TypeSF;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private String nom;
    private String prenom;
    private String email;
    private Date dateDeNaissance;
    private LIEU_NAISSANCE lieuNaiss;
    private SEXE sexe;
    private TypeSF situationFamiliale;

}
