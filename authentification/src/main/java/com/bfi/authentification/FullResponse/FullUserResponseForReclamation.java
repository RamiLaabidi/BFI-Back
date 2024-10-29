package com.bfi.authentification.FullResponse;

import com.bfi.authentification.DTO.Reclamation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullUserResponseForReclamation {
    private String nom;
    private String prenom;
    List<Reclamation> reclamations;


}
