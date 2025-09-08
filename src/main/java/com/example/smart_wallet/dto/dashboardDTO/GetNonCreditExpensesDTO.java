package com.example.smart_wallet.dto.dashboardDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetNonCreditExpensesDTO {
    private UUID expenseId;
    private String expenseDescription;
    private BigDecimal expenseCost;
}
