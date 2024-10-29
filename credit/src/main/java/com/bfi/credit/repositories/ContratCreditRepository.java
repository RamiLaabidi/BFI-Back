package com.bfi.credit.repositories;

import com.bfi.credit.entities.ContratCredit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratCreditRepository extends JpaRepository<ContratCredit, Long> {
    // Additional query methods can be defined here if needed
}
