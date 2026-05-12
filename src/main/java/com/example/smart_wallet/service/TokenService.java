package com.example.smart_wallet.service;

import com.example.smart_wallet.domain.entity.User;

public interface TokenService {
    String generateToken(User user);

    String validateToken(String token);

    String getWalletIdFromToken(String token);
}
