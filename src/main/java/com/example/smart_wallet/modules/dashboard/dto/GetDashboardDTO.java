package com.example.smart_wallet.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetDashboardDTO {
    BigDecimal total;
    List<GetDashboardExpenseDTO> expenses;
}
