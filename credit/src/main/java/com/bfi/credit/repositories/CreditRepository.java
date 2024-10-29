package com.bfi.credit.repositories;

import com.bfi.credit.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findAllByIdUser(Long idUser);
}
