package com.example.smart_wallet.modules.card.application.port.out;

import com.example.smart_wallet.modules.card.domain.entity.CardType;

import java.util.List;
import java.util.UUID;

public interface CardTypeRepository {
    List<CardType> findAll();

    CardType getCardTypeById(UUID id);
}
