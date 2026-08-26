package com.example.smart_wallet.modules.user.application.port.out;

import com.example.smart_wallet.modules.user.application.dto.GoogleUserInfo;

public interface GoogleIdentityProvider {
    GoogleUserInfo verify(String idToken);
}
