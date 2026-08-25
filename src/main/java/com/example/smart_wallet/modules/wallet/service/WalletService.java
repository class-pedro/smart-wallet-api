package com.example.smart_wallet.modules.wallet.service;

import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;

public interface WalletService {
    public void createWallet(User user);

    public Wallet getById(String id);
}
