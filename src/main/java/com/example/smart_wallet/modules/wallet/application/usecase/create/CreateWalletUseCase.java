package com.example.smart_wallet.modules.wallet.application.usecase.create;

import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;

public interface CreateWalletUseCase {
    Wallet execute(User user);
}
