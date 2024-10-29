package com.bfi.credit.repositories;

import com.bfi.credit.entities.DocAttache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocAttacheRepository extends JpaRepository<DocAttache, Long> {
    // Additional query methods can be defined here if needed
}
