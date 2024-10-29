package com.bfi.authentification.controllers;

import com.bfi.authentification.DTO.UpdateUserDTO;
import com.bfi.authentification.FullResponse.FullUserResponseForCredit;
import com.bfi.authentification.FullResponse.FullUserResponseForRdv;
import com.bfi.authentification.FullResponse.FullUserResponseForReclamation;
import com.bfi.authentification.entities.ChangePasswordRequest;
import com.bfi.authentification.entities.CompteBancaire;
import com.bfi.authentification.entities.User;
import com.bfi.authentification.services.FileStorageService;
import com.bfi.authentification.services.UserService;
import com.bfi.authentification.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Value("${upload.directory}")
    private String uploadDir;
    private final IUserService userService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private final ResourceLoader resourceLoader;

    @Autowired
    public UserController(UserService userService, ResourceLoader resourceLoader)  {
        this.resourceLoader = resourceLoader;
        this.userService = userService;
    }

    @PostMapping("/add")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.updateUser(id, updateUserDTO);
    }

    @DeleteMapping("/delete/{idU}")
    public ResponseEntity<String> deleteUser(@PathVariable("idU") Long idU) {
        boolean deleted = userService.deleteUser(idU);
        if (deleted) {
            return ResponseEntity.ok("User deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + idU + " does not exist.");
        }
    }

    @GetMapping("/")
    private List<User> getUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/clients")
    public List<User> getAllClients() {
        return userService.getAllClients();
    }

    @GetMapping("/cin/{cin}")
    public ResponseEntity<User> getUserByCin(@PathVariable Long cin) {
        return ResponseEntity.ok(userService.getUserByCin(cin));
    }
    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }



    ////////////////////////////////////////////////////////////////////////

    @GetMapping("/user&reclamation/{idUser}")
    public ResponseEntity<FullUserResponseForReclamation> findAllUsersReclamation(@PathVariable("idUser") Long idUser) {
        return ResponseEntity.ok(userService.findUserWithReclamations(idUser));
    }

    @GetMapping("/user&credit/{idUser}")
    public ResponseEntity<FullUserResponseForCredit> findAllUsersCredit(@PathVariable("idUser") Long idUser) {
        return ResponseEntity.ok(userService.findUserWithCredits(idUser));
    }

    @GetMapping("/user&rdv/{idUser}")
    public ResponseEntity<FullUserResponseForRdv> findAllUsersRdv(@PathVariable("idUser") Long idUser) {
        return ResponseEntity.ok(userService.findUserWithRdvs(idUser));
    }




    //////////////////////////////////////////////////////////
    @GetMapping("/nbreClients")
    public ResponseEntity<Integer> getNbreClients() {
        Integer nbreClients = userService.calculateNbreClients();
        return ResponseEntity.ok(nbreClients);
    }
    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/profile-picture/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getProfilePicture(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .body(resource);
            } else {
                throw new RuntimeException("File not found " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @PostMapping("/uploadProfilePicture/{userId}")
    public ResponseEntity<String> uploadImage(@PathVariable ("userId") Long userId ,@RequestParam("file") MultipartFile file) {
        try {
            String message = fileStorageService.uploadImage(userId,file);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }
    @PostMapping("/compte/{userId}")
    public CompteBancaire addCompteToUser(@PathVariable Long userId, @RequestBody CompteBancaire compteBancaire) {
        return userService.creerComptePourClient(userId, compteBancaire);
    }

}

