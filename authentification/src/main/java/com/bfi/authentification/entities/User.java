package com.bfi.authentification.entities;
import com.bfi.authentification.entities.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(exclude = "compteBancaires")
@Table(name="usert")
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idU;
    @Enumerated(EnumType.STRING)
    Role role;
    String nom;
    String prenom;
    Date dateDeNaissance;
    @Enumerated(EnumType.STRING)
    TypeSF situationFamiliale;
    String profilePicture;
    Long numCin;
    @Enumerated(EnumType.STRING)
    LIEU_NAISSANCE lieuNaiss;
    @Enumerated(EnumType.STRING)
    SEXE sexe;
    String email;
    String motDePasse;
    Double revenuMensuel;
    Double chargesMensuelles;
    Double salaire;
    @Enumerated(EnumType.STRING)
    EmploymentType employmentType;
    Integer age;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("user")
    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CompteBancaire> compteBancaires;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    //////////
    public int getAge() {
        if (this.dateDeNaissance == null) {
            return 0;
        }
        LocalDate birthDate = this.dateDeNaissance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




}
