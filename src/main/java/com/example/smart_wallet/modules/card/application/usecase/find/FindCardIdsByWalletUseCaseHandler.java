package com.example.smart_wallet.modules.card.application.usecase.find;

import com.example.smart_wallet.modules.card.application.port.out.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FindCardIdsByWalletUseCaseHandler implements FindCardIdsByWalletUseCase {

    private final CardRepository cardRepository;

    @Override
    public List<UUID> execute(UUID walletId) {
        return cardRepository.findIdsByWalletId(walletId);
    }
}
