package com.example.smart_wallet.service;

import com.example.smart_wallet.domain.entity.Card;
import com.example.smart_wallet.dto.CreateCardDTO;
import com.example.smart_wallet.dto.GetCardIdAndNameDTO;

import java.util.List;

public interface CardService {
    Card getById(String cardId);

    List<GetCardIdAndNameDTO> getCardIdAndNameById(String cardId);

    void create(CreateCardDTO createCardDTO);
}
