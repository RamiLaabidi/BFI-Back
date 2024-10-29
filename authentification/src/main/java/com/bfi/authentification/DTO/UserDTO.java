package com.bfi.authentification.DTO;

import com.bfi.authentification.entities.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private double revenuMensuel;
    private double salaire;
    private double chargesMensuelles;
    private Integer age;
    private EmploymentType employmentType;
}
