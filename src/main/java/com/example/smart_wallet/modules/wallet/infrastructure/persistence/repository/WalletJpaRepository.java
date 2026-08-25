package com.example.smart_wallet.modules.wallet.infrastructure.persistence.repository;

import com.example.smart_wallet.modules.wallet.application.port.out.WalletRepository;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletJpaRepository extends JpaRepository<Wallet, UUID>, WalletRepository {
}
