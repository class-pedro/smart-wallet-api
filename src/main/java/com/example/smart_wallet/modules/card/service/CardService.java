package com.example.smart_wallet.modules.card.service;

import com.example.smart_wallet.modules.card.domain.entity.Card;
import com.example.smart_wallet.modules.card.dto.CreateCardDTO;
import com.example.smart_wallet.modules.card.dto.GetCardIdAndNameDTO;

import java.util.List;

public interface CardService {
    Card getById(String cardId);

    List<GetCardIdAndNameDTO> getCardIdAndNameById(String cardId);

    void create(CreateCardDTO createCardDTO);
}
