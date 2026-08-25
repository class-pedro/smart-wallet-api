package com.example.smart_wallet.modules.expense.application.dto;

public record ImportExpenseFromNfcCommand(
        String url,
        String paymentType,
        String walletId,
        String cardId
) {
}
