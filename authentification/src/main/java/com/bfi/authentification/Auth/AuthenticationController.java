package com.bfi.authentification.Auth;

import com.bfi.authentification.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody LoginRequest request
  ) {
    return ResponseEntity.ok(service.login(request));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<Object> forgotPassword(@RequestParam("email") String email) {
    Optional<User> optionalUser = service.findUserByEmail(email);
    if (!optionalUser.isPresent()) {
      return ResponseEntity.badRequest().body("User not found");
    }

    User user = optionalUser.get();
    String token = UUID.randomUUID().toString();
    service.createPasswordResetTokenForUser(user, token);

    String resetUrl = "http://localhost:4200/reset-password/" + token;
    service.sendEmail(user.getEmail(), "Reset Password", "To reset your password, click the link below:\n" + resetUrl);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> payload) {
    String token = payload.get("token");
    String password = payload.get("password");
    try {
      service.resetPassword(token, password);
      Map<String, String> response = new HashMap<>();
      response.put("message", "Password reset successfully");
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
    }
  }
}
