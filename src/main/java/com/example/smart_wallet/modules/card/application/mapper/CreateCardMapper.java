package com.example.smart_wallet.modules.card.application.mapper;

import com.example.smart_wallet.modules.card.application.dto.CreateCardCommand;
import com.example.smart_wallet.modules.card.domain.entity.Card;

public class CreateCardMapper {
    public static Card toEntity(CreateCardCommand createCardCommand) {
        Card card = new Card();
        card.setName(createCardCommand.name());
        card.setBalance(createCardCommand.balance());
        card.setDueDateDay(createCardCommand.dueDateDay());
        card.setClosingDateDay(createCardCommand.closingDateDay());
        card.setCreditLimit(createCardCommand.creditLimit());

        return card;
    }
}
