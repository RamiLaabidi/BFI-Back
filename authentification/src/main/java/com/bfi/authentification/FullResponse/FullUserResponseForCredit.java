package com.bfi.authentification.FullResponse;

import com.bfi.authentification.DTO.CreditDTO;
import com.bfi.authentification.entities.enums.Role;
import com.bfi.authentification.entities.enums.TypeSF;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullUserResponseForCredit {
    @Enumerated(EnumType.STRING)
    private Role role;
    private String nom;
    private String prenom;
    private Long numCin;
    private Date dateDeNaissance;
    private TypeSF situationFamiliale;
    List<CreditDTO> creditDTOS;


}
