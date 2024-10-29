package com.bfi.reclamation.repositories;

import com.bfi.reclamation.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findAllByIdUser(Long idUser);
}
