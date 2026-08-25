package com.example.smart_wallet.modules.card.application.dto;

import java.util.UUID;

public record CreateCardCommand(
        String name,
        Integer balance,
        Integer dueDateDay,
        Integer closingDateDay,
        Integer creditLimit,
        UUID cardTypeId,
        UUID walletId
) {
}
