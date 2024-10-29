package com.bfi.authentification.services.interfaces;
import com.bfi.authentification.DTO.UpdateUserDTO;
import com.bfi.authentification.FullResponse.FullUserResponseForCredit;
import com.bfi.authentification.FullResponse.FullUserResponseForRdv;
import com.bfi.authentification.FullResponse.FullUserResponseForReclamation;
import com.bfi.authentification.entities.ChangePasswordRequest;
import com.bfi.authentification.entities.CompteBancaire;
import com.bfi.authentification.entities.User;

import java.security.Principal;
import java.util.List;

public interface IUserService {
     User saveUser(User user);
     CompteBancaire creerComptePourClient(Long userId, CompteBancaire compteBancaire);
     User getUserById(Long userId);
     User getUserByCin(Long cin);
     List<User> getAllUsers();
    List<User> getAllClients();
    Boolean deleteUser(Long idU);
    User updateUser(Long userId, UpdateUserDTO updateUserDTO);
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
    Integer calculateNbreClients();
    FullUserResponseForCredit findUserWithCredits(Long idUser);
    FullUserResponseForReclamation findUserWithReclamations(Long idUser);
    FullUserResponseForRdv findUserWithRdvs(Long idUser);
}

