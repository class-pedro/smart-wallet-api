package com.example.smart_wallet.modules.user.infrastructure.persistence.repository;

import com.example.smart_wallet.modules.user.application.port.out.UserRepository;
import com.example.smart_wallet.modules.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID>, UserRepository {
    @Override
    UserDetails findByEmail(String email);
}
