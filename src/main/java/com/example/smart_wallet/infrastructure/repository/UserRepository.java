package com.example.smart_wallet.infrastructure.repository;

import com.example.smart_wallet.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
