package com.example.smart_wallet.modules.wallet.application.usecase.create;

import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.wallet.application.port.out.WalletRepository;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateWalletUseCaseHandler implements CreateWalletUseCase {
    private final WalletRepository walletRepository;

    @Override
    public Wallet execute(User user) {
        Wallet wallet = new Wallet();

        wallet.setUser(user);
        wallet.setBalance(0);

        return walletRepository.save(wallet);
    }
}
