package com.example.smart_wallet.infrastructure.repository;

import com.example.smart_wallet.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
