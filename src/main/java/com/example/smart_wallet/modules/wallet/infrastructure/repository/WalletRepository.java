package com.example.smart_wallet.modules.wallet.infrastructure.repository;

import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
