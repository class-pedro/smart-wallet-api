package com.example.smart_wallet.modules.expense.application.dto;

import java.util.List;

public record GetTransactionsDTO(
        GetTransactionsSummaryDTO summary,
        List<GetExpenseListItemDTO> transactions
) {
}
