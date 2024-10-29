package com.bfi.authentification.services;

import com.bfi.authentification.Client.CreditClient;
import com.bfi.authentification.Client.RdvClient;
import com.bfi.authentification.Client.ReclamationClient;
import com.bfi.authentification.DTO.UpdateUserDTO;
import com.bfi.authentification.FullResponse.FullUserResponseForCredit;
import com.bfi.authentification.FullResponse.FullUserResponseForRdv;
import com.bfi.authentification.FullResponse.FullUserResponseForReclamation;
import com.bfi.authentification.entities.ChangePasswordRequest;
import com.bfi.authentification.entities.CompteBancaire;
import com.bfi.authentification.entities.User;
import com.bfi.authentification.entities.enums.Role;
import com.bfi.authentification.repositories.CompteBancaireRepository;
import com.bfi.authentification.repositories.UserRepository;
import com.bfi.authentification.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserService implements IUserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CompteBancaireRepository compteBancaireRepository;

    private final CreditClient creditClient;
    private  final ReclamationClient reclamationClient;
    private  final RdvClient rdvClient;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public CompteBancaire creerComptePourClient(Long userId, CompteBancaire compteBancaire) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        compteBancaire.setUser(user);
        user.getCompteBancaires().add(compteBancaire);
        return compteBancaireRepository.save(compteBancaire);
    }


    public User getUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByCin(Long cin) {
        return userRepository.findByNumCin(cin)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<User> getAllClients() {
        return  userRepository.findByRole(Role.CLIENT);
    }
    public Boolean deleteUser(Long idU) {
        Optional<User> UserOptional = userRepository.findById(idU);
        if (UserOptional.isPresent()) {
            userRepository.deleteById(idU);
            return true; // La suppression a été effectuée avec succès
        } else {
            return false; // L'identifiant spécifié n'existe pas
        }
    }


    public FullUserResponseForRdv findUserWithRdvs(Long idUser) {
        var user = userRepository.findById(idUser)
                .orElse(
                        User.builder()
                                .nom("NOT_FOUND")
                                .prenom("NOT_FOUND")
                                .build()
                );
        var rendezVous = rdvClient.findAllRdvByUser(idUser);
        return FullUserResponseForRdv.builder()
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .rendezVous(rendezVous)
                .build();
    }


    public FullUserResponseForReclamation findUserWithReclamations(Long idUser) {
        var user = userRepository.findById(idUser)
                .orElse(
                        User.builder()
                                .nom("NOT_FOUND")
                                .prenom("NOT_FOUND")
                                .build()
                );
        var reclamations = reclamationClient.findAllReclamationByUser(idUser);
        return FullUserResponseForReclamation.builder()
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .reclamations(reclamations)
                .build();
    }


    public FullUserResponseForCredit findUserWithCredits(Long idUser) {
        var user = userRepository.findById(idUser)
                .orElse(
                        User.builder()
                                .nom("NOT_FOUND")
                                .prenom("NOT_FOUND")
                                .build()
                );
        var credits = creditClient.findAllCreditsByUser(idUser);
        return FullUserResponseForCredit.builder()
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .role(user.getRole())
                .numCin(user.getNumCin())
                .dateDeNaissance(user.getDateDeNaissance())
                .situationFamiliale(user.getSituationFamiliale())
                .creditDTOS(credits)
                .build();
    }
    public User updateUser(Long userId, UpdateUserDTO updateUserDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (updateUserDTO.getNom() != null) {
                user.setNom(updateUserDTO.getNom());
            }
            if (updateUserDTO.getPrenom() != null) {
                user.setPrenom(updateUserDTO.getPrenom());
            }
            if (updateUserDTO.getEmail() != null) {
                user.setEmail(updateUserDTO.getEmail());
            }
            if (updateUserDTO.getDateDeNaissance() != null) {
                user.setDateDeNaissance(updateUserDTO.getDateDeNaissance());
            }
            if (updateUserDTO.getLieuNaiss() != null) {
                user.setLieuNaiss(updateUserDTO.getLieuNaiss());
            }
            if (updateUserDTO.getSexe() != null) {
                user.setSexe(updateUserDTO.getSexe());
            }
            if (updateUserDTO.getSituationFamiliale() != null) {
                user.setSituationFamiliale(updateUserDTO.getSituationFamiliale());
            }
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }


    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setMotDePasse(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
    }

    public Integer calculateNbreClients() {
        List<User> users = userRepository.findAll();
        int nbreClients = 0;
        for (User user : users) {
            if (Role.CLIENT.equals(user.getRole())) {
                nbreClients++;
            }
        }
        return nbreClients;
    }

}
