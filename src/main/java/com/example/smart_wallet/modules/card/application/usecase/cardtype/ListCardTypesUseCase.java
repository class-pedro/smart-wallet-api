package com.example.smart_wallet.modules.card.application.usecase.cardtype;

import com.example.smart_wallet.modules.card.domain.entity.CardType;

import java.util.List;

public interface ListCardTypesUseCase {
    List<CardType> execute();
}
