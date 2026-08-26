package com.example.smart_wallet.modules.user.application.usecase.completeProfile;

import com.example.smart_wallet.modules.user.application.dto.CompleteProfileCommand;

public interface CompleteProfileUseCase {
    String execute(String authHeader, CompleteProfileCommand command);
}
