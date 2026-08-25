package com.example.smart_wallet.modules.wallet.application.usecase.find;

import com.example.smart_wallet.modules.wallet.application.port.out.WalletRepository;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindWalletUseCaseHandler implements FindWalletUseCase {
    private final WalletRepository walletRepository;

    @Override
    public Wallet execute(String walletId) {
        if (walletId == null) return null;
        UUID walletUUID = UUID.fromString(walletId);

        return walletRepository.findById(walletUUID).orElse(null);
    }
}
