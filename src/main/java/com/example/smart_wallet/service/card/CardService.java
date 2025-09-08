package com.example.smart_wallet.service.card;

import com.example.smart_wallet.domain.entity.Card;
import com.example.smart_wallet.dto.CreateCardDTO;

import java.util.Optional;

public interface CardService {
    public Card getById(String cardId);

    public void create(CreateCardDTO createCardDTO);
}
