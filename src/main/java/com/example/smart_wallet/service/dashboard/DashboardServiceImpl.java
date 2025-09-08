package com.example.smart_wallet.service.dashboard;

import com.example.smart_wallet.dto.dashboardDTO.GetCreditCardStatementsDTO;
import com.example.smart_wallet.dto.dashboardDTO.GetDashboardDTO;
import com.example.smart_wallet.dto.dashboardDTO.GetDashboardExpenseDTO;
import com.example.smart_wallet.dto.dashboardDTO.GetNonCreditExpensesDTO;
import com.example.smart_wallet.infrastructure.repository.CardRepository.CardRepository;
import com.example.smart_wallet.infrastructure.repository.ExpenseRepository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    // TODO: Chamar o Expense Service pra ipso ou então criar um dashboard repository com as queries
    private ExpenseRepository expenseRepository;
    private CardRepository cardRepository;

    public GetDashboardDTO getDashboard(UUID walletId, Integer year, Integer month) {
        BigDecimal sumOfExpenses = BigDecimal.ZERO;
        List<UUID> cardIds = cardRepository.findIdsByWalletId(walletId);
        List<GetCreditCardStatementsDTO> statements = expenseRepository.findStatementsByCards(year,
                month, cardIds);
        List<GetNonCreditExpensesDTO> recurrentExpenses = expenseRepository.findNonCreditRecurrentExpenses();
        List<GetNonCreditExpensesDTO> payInFullExpenses = expenseRepository.findPayInFullExpensesByMonthAndYear(year,
                month);
        List<GetDashboardExpenseDTO> dashboardExpenses = new ArrayList<>();

        for (GetCreditCardStatementsDTO statement : statements) {
            GetDashboardExpenseDTO dashboardExpense = new GetDashboardExpenseDTO(
                    statement.getCardId(),
                    statement.getCardName(),
                    normalizeBigDecimalToDashboard(statement.getTotalStatement())
            );
            sumOfExpenses = sumOfExpenses.add(statement.getTotalStatement());
            dashboardExpenses.add(dashboardExpense);
        }

        for (GetNonCreditExpensesDTO expense : recurrentExpenses) {
            GetDashboardExpenseDTO dashboardExpense = new GetDashboardExpenseDTO(
                    expense.getExpenseId(),
                    expense.getExpenseDescription(),
                    normalizeBigDecimalToDashboard(expense.getExpenseCost())
            );

            sumOfExpenses = sumOfExpenses.add(expense.getExpenseCost());
            dashboardExpenses.add(dashboardExpense);
        }

        for (GetNonCreditExpensesDTO payInFullExpense : payInFullExpenses) {
            GetDashboardExpenseDTO dashboardExpense = new GetDashboardExpenseDTO(
                    payInFullExpense.getExpenseId(),
                    payInFullExpense.getExpenseDescription(),
                    normalizeBigDecimalToDashboard(payInFullExpense.getExpenseCost())
            );

            sumOfExpenses = sumOfExpenses.add(payInFullExpense.getExpenseCost());
            dashboardExpenses.add(dashboardExpense);
        }

        return new GetDashboardDTO(
                normalizeBigDecimalToDashboard(sumOfExpenses),
                dashboardExpenses
        );
    }

    private static BigDecimal normalizeBigDecimalToDashboard(BigDecimal valor) {
        if (valor == null) {
            return null;
        }
        return valor
                .divide(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.DOWN);
    }
}
