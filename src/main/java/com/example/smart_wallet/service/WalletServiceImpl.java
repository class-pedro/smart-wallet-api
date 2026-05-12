package com.example.smart_wallet.service;

import com.example.smart_wallet.domain.entity.User;
import com.example.smart_wallet.domain.entity.Wallet;
import com.example.smart_wallet.infrastructure.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    public void createWallet(User user) {
        Wallet wallet = new Wallet();

        wallet.setUser(user);
        wallet.setBalance(0);

        walletRepository.save(wallet);
    }

    @Override
    public Wallet getById(String walletId) {
        if (walletId == null) return null;
        UUID walletUUID = UUID.fromString(walletId);

        return walletRepository.findById(walletUUID).orElse(null);
    }
}
