package com.example.smart_wallet.service.wallet;

import com.example.smart_wallet.domain.entity.User;
import com.example.smart_wallet.domain.entity.Wallet;

import java.util.Optional;

public interface WalletService {
    public void createWallet(User user);

    public Wallet getById(String id);
}
