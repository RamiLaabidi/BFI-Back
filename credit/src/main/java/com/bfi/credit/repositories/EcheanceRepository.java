package com.bfi.credit.repositories;

import com.bfi.credit.entities.Echeance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcheanceRepository extends JpaRepository<Echeance, Long> {
    // Additional query methods can be defined here if needed
}
