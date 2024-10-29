package com.bfi.authentification.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userId")
    private User user;

    private Date expiryDate;

    public PasswordResetToken() {}

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(60 * 24); // Set expiry date to 24 hours from now
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        final Date now = new Date();
        return new Date(now.getTime() + (expiryTimeInMinutes * 60 * 1000));
    }

    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }

    // Getters and setters
}