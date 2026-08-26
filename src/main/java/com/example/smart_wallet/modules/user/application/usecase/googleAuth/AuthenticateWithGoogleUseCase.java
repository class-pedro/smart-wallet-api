package com.example.smart_wallet.modules.user.application.usecase.googleAuth;

import com.example.smart_wallet.modules.user.application.dto.GoogleUserInfo;

public interface AuthenticateWithGoogleUseCase {
    String execute(GoogleUserInfo googleUserInfo);
}
