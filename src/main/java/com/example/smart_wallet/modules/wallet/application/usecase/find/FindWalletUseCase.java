package com.example.smart_wallet.modules.wallet.application.usecase.find;

import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;

public interface FindWalletUseCase {
    Wallet execute(String walletId);
}
