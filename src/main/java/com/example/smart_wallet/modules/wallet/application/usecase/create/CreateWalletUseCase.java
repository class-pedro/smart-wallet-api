package com.example.smart_wallet.modules.wallet.application.usecase.create;

import com.example.smart_wallet.modules.user.domain.entity.User;

public interface CreateWalletUseCase {
    void execute(User user);
}
