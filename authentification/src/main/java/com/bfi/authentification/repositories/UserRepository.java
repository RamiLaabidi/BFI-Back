package com.bfi.authentification.repositories;

import com.bfi.authentification.entities.User;
import com.bfi.authentification.entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findFirstByEmail(String email);
    Optional<User> findByNumCin(Long numCin);
    List<User> findByRole(Role role);


}
