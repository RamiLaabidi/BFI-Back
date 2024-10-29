package com.bfi.authentification.LoanEvaluation;

import com.bfi.authentification.DTO.CreditDTO;
import com.bfi.authentification.entities.CompteBancaire;
import com.bfi.authentification.entities.User;

import lombok.Data;

@Data
public class LoanEvaluationRequest {
    private User user;
    private CompteBancaire compteBancaire;
    private CreditDTO creditDTO;
}
