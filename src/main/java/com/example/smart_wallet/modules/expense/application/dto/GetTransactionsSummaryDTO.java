package com.example.smart_wallet.modules.expense.application.dto;

import java.math.BigDecimal;

public record GetTransactionsSummaryDTO(
        BigDecimal currentBalance,
        BigDecimal monthExpenses,
        BigDecimal monthIncome
) {
}
