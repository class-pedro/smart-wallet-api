package com.example.smart_wallet.modules.card.application.usecase.find;

import com.example.smart_wallet.modules.card.domain.entity.Card;

public interface FindCardUseCase {
    Card execute(String cardId);
}
