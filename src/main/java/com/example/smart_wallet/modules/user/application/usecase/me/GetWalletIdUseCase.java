package com.example.smart_wallet.modules.user.application.usecase.me;

public interface GetWalletIdUseCase {
    String execute(String authHeader);
}
