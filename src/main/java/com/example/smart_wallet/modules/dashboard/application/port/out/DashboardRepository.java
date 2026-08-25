package com.example.smart_wallet.modules.dashboard.application.port.out;

import com.example.smart_wallet.modules.dashboard.application.dto.GetCreditCardStatementsDTO;
import com.example.smart_wallet.modules.dashboard.application.dto.GetNonCreditExpensesDTO;

import java.util.List;
import java.util.UUID;

public interface DashboardRepository {
    List<GetCreditCardStatementsDTO> findStatementsByCards(Integer year, Integer month, List<UUID> cardIds);

    List<GetNonCreditExpensesDTO> findNonCreditRecurrentExpenses();

    List<GetNonCreditExpensesDTO> findPayInFullExpensesByMonthAndYear(Integer year, Integer month);
}
