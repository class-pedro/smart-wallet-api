package com.example.smart_wallet.modules.user.application.usecase.login;

import com.example.smart_wallet.modules.user.domain.entity.User;

public interface GenerateTokenUseCase {
    String execute(User user);
}
