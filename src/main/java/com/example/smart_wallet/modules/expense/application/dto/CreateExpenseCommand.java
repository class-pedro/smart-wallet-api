package com.example.smart_wallet.modules.expense.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateExpenseCommand(
        String description,
        BigDecimal cost,
        String paymentType,
        String paymentMethod,
        LocalDateTime paymentDate,
        LocalDateTime purchaseDate,
        Integer installments,
        String status,
        String walletId,
        String cardId
) {
}
