package com.bfi.authentification.DTO;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RendezVous {
    private Date dateRdv;
    @Enumerated(EnumType.STRING)
    private Mode mode;
    private String sujet;
}
