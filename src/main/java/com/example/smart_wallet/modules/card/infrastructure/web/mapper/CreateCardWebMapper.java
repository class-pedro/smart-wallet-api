package com.example.smart_wallet.modules.card.infrastructure.web.mapper;

import com.example.smart_wallet.modules.card.application.dto.CreateCardCommand;
import com.example.smart_wallet.modules.card.infrastructure.web.dto.CreateCardRequest;

public class CreateCardWebMapper {
    public static CreateCardCommand toCommand(CreateCardRequest request) {
        return new CreateCardCommand(
                request.getName(),
                request.getBalance(),
                request.getDueDateDay(),
                request.getClosingDateDay(),
                request.getCreditLimit(),
                request.getCardTypeId(),
                request.getWalletId()
        );
    }
}
