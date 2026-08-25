package com.example.smart_wallet.modules.wallet.application.port.out;

import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    Optional<Wallet> findById(UUID id);

    Wallet save(Wallet wallet);
}
