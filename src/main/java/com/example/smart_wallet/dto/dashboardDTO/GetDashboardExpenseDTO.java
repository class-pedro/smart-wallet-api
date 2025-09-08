package com.example.smart_wallet.dto.dashboardDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetDashboardExpenseDTO {
    private UUID dashboardExpenseId;
    private String dashboardExpenseDescription;
    private BigDecimal dashboardExpenseCost;
}