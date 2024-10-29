package com.bfi.authentification.FullResponse;

import com.bfi.authentification.DTO.RendezVous;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullUserResponseForRdv {
    private String nom;
    private String prenom;
    List<RendezVous> rendezVous;


}
