package com.bfi.authentification.Auth;
import com.bfi.authentification.entities.PasswordResetToken;
import com.bfi.authentification.repositories.PasswordResetTokenRepository;
import com.bfi.authentification.services.JwtService;
import com.bfi.authentification.entities.User;
import com.bfi.authentification.entities.enums.Role;
import com.bfi.authentification.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository iUserRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final PasswordResetTokenRepository tokenRepository;
  private final JavaMailSender mailSender;




  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
            .nom(request.getNom())
            .prenom(request.getPrenom())
            .numCin(request.getNumCin())
            .dateDeNaissance(request.getDateDeNaissance())
            .email(request.getEmail())
            .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
            .role(Role.CLIENT)
            .build();
    iUserRepository.save(user);

    var jwtToken = jwtService.generateToken(user, user.getUsername());

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .build();
  }

  public AuthenticationResponse login(LoginRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getMotDePasse()
            )
    );
    var user = iUserRepository.findFirstByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
    Map<String, Object> claims = new HashMap<>();
    String jwtToken = jwtService.generateToken(user, user.getUsername());

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .build();
  }




  public void createPasswordResetTokenForUser(User user, String token) {
    PasswordResetToken myToken = new PasswordResetToken(token, user);
    tokenRepository.save(myToken);
  }

  public void sendEmail(String recipientAddress, String subject, String message) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(recipientAddress);
    email.setSubject(subject);
    email.setText(message);
    mailSender.send(email);
  }

  public Optional<User> findUserByEmail(String email) {
    return iUserRepository.findFirstByEmail(email);
  }

  public void resetPassword(String token, String newPassword) {
    PasswordResetToken resetToken = tokenRepository.findByToken(token);
    if (resetToken == null || resetToken.isExpired()) {
      throw new IllegalArgumentException("Invalid or expired token");
    }

    User user = resetToken.getUser();
    user.setMotDePasse(passwordEncoder.encode(newPassword));
    iUserRepository.save(user);

    tokenRepository.delete(resetToken);
  }
}
