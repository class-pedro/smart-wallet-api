package com.example.smart_wallet.modules.card.application.usecase.find;

import com.example.smart_wallet.modules.card.application.dto.GetCardDetailsDTO;
import com.example.smart_wallet.modules.card.application.port.out.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FindCardDetailsByWalletUseCaseHandler implements FindCardDetailsByWalletUseCase {

    private final CardRepository cardRepository;

    @Override
    public List<GetCardDetailsDTO> execute(String walletId) {
        UUID walletUUID = UUID.fromString(walletId);
        return cardRepository.findDetailsByWalletId(walletUUID);
    }
}
