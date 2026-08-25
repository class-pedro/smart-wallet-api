package com.example.smart_wallet.modules.user.service;

import com.example.smart_wallet.modules.user.domain.entity.User;

public interface TokenService extends com.example.smart_wallet.infrastructure.security.TokenService {
    String generateToken(User user);

    String getWalletIdFromToken(String token);
}
