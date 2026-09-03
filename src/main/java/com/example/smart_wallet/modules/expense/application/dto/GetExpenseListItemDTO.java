package com.example.smart_wallet.modules.expense.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetExpenseListItemDTO(
        UUID id,
        LocalDateTime purchaseDate,
        String description,
        String paymentType,
        String paymentMethod,
        Integer installmentNumber,
        Integer installments,
        BigDecimal amount,
        String status
) {
}
