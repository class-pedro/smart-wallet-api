package com.example.smart_wallet.modules.card.application.usecase.find;

import com.example.smart_wallet.modules.card.application.port.out.CardRepository;
import com.example.smart_wallet.modules.card.domain.entity.Card;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindCardUseCaseHandler implements FindCardUseCase {

    private final CardRepository cardRepository;

    @Override
    public Card execute(String cardId) {
        if (cardId == null) return null;
        UUID cardUUID = UUID.fromString(cardId);
        return cardRepository.findById(cardUUID).orElse(null);
    }
}
