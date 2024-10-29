package com.bfi.authentification.LoanEvaluation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evaluation")
@RequiredArgsConstructor
public class LoanEvaluationController {
    private final LoanEvaluationService loanEvaluationService;


    @PostMapping("/calculateScore")
    public ResponseEntity<Double> calculateScore(@RequestBody LoanEvaluationRequest request) {
        double score = loanEvaluationService.calculateScore(request.getUser());
        return ResponseEntity.ok(score);
    }

    @PostMapping("/evaluate")
    public ResponseEntity<String> evaluateRisk(@RequestBody LoanEvaluationRequest request ) {

        String riskLevel = loanEvaluationService.evaluateRisk( request.getCompteBancaire(), request.getCreditDTO());
        return ResponseEntity.ok(riskLevel);

    }


    @PostMapping("/approveCredit")
    public ResponseEntity<String> approveCredit(@RequestBody LoanEvaluationRequest request) {
        String approvalStatus = loanEvaluationService.approveCredit(request.getUser(), request.getCompteBancaire(), request.getCreditDTO());
        return ResponseEntity.ok(approvalStatus);
    }
}
