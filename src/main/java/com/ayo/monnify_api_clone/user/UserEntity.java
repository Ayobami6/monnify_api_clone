package com.ayo.monnify_api_clone.user;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ayo.monnify_api_clone.utils.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "Varchar(255)")
    private Role role = Role.USER;
    @Column(name = "contractCode", nullable = false,  updatable = false)
    private Long contractCode;
    @Column(name = "apiKey", unique = true)
    private String apiKey;
    @Column(name = "secretKey", unique = true)
    private String secretKey;
    private Long walletAccountNumber;
    @Builder.Default
    @Column(columnDefinition = "FLOAT DEFAULT 0.0")
    private double walletBalance = 0.0;
    private String firstName;
    private String lastName;
    @Column(name = "webhookUrl", nullable = true)
    private String webhookUrl;
    private String walletBankName;
    private String walletAccountName;

    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "mode", columnDefinition = "Varchar(255)")
    private Mode mode = Mode.TEST;
    
    @Builder.Default
    @Column(name = "verified", nullable = false)
    private boolean verified = false;
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        this.contractCode = Utils.generateRandomNumber();
        if (mode == Mode.TEST) {
            this.apiKey = "MK_TEST_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
        } else {
            this.apiKey = "MK_PROD_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
        }
        this.secretKey = UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase();
        this.walletAccountNumber = Utils.generateRandomNumber();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.walletBankName = "WEMA";
        this.walletAccountName = this.getFirstName() + " " + this.getLastName();
    }

    @PreUpdate
    protected void update() {
        this.updatedAt = LocalDateTime.now();
        if (mode == Mode.TEST) {
            this.apiKey = "MK_PROD_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
        }
    }

    public void fund(float amount) {
        this.walletBalance += amount;
    }

    public void withdraw(float amount) {
        if (this.walletBalance >= amount) {
            this.walletBalance -= amount;
        } else {
            throw new IllegalStateException("Insufficient funds in the wallet");
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return UserDetails.super.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return secretKey;
    }

    @Override
    public String getUsername() {
        return apiKey;
    }

}
