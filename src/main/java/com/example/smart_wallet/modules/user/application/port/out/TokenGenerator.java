package com.example.smart_wallet.modules.user.application.port.out;

import com.example.smart_wallet.modules.user.domain.entity.User;

public interface TokenGenerator {
    String generateToken(User user);

    String getWalletIdFromToken(String token);

    String getEmailFromToken(String token);
}
