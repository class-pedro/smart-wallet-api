package com.example.smart_wallet.modules.card.application.usecase.find;

import com.example.smart_wallet.modules.card.application.dto.GetCardIdAndNameDTO;
import com.example.smart_wallet.modules.card.application.port.out.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FindCardIdsAndNameByWalletUseCaseHandler implements FindCardIdsAndNameByWalletUseCase {

    private final CardRepository cardRepository;

    @Override
    public List<GetCardIdAndNameDTO> execute(String walletId) {
        UUID walletUUID = UUID.fromString(walletId);
        return cardRepository.findIdsAndNameByWalletId(walletUUID);
    }
}
