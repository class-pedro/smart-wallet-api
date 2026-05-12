package com.example.smart_wallet.service;

import com.example.smart_wallet.domain.entity.User;
import com.example.smart_wallet.domain.entity.Wallet;

public interface WalletService {
    public void createWallet(User user);

    public Wallet getById(String id);
}
