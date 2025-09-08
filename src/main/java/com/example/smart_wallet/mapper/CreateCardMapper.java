package com.example.smart_wallet.mapper;

import com.example.smart_wallet.domain.entity.Card;
import com.example.smart_wallet.dto.CreateCardDTO;

public class CreateCardMapper {
    public static Card toEntity(CreateCardDTO createCardDTO) {
        Card card = new Card();
        card.setName(createCardDTO.getName());
        card.setBalance(createCardDTO.getBalance());
        card.setDueDateDay(createCardDTO.getDueDateDay());
        card.setClosingDateDay(createCardDTO.getClosingDateDay());
        card.setCreditLimit(createCardDTO.getCreditLimit());

        return card;
    }
}
