package com.example.smart_wallet.modules.card.infrastructure.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record GetCardDetailsResponseDTO(
        UUID id,
        String name,
        BigDecimal creditLimit,
        BigDecimal currentInvoice,
        String dueDateLabel,
        String status
) {
}
