package com.example.smart_wallet.dto.dashboardDTO;

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
