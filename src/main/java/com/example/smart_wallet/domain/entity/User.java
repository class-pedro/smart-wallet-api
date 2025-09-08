package com.example.smart_wallet.domain.entity;

import com.example.smart_wallet.domain.valueObject.Cellphone;
import com.example.smart_wallet.domain.valueObject.Cpf;
import com.example.smart_wallet.domain.valueObject.Email;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String passwordHash;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String cellphone;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = true)
    private Wallet wallet;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
