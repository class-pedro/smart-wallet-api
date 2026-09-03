package com.example.smart_wallet.modules.dashboard.application.usecase.dashboard;

import com.example.smart_wallet.modules.card.application.usecase.find.FindCardIdsByWalletUseCase;
import com.example.smart_wallet.modules.dashboard.application.dto.GetCreditCardStatementsDTO;
import com.example.smart_wallet.modules.dashboard.application.dto.GetDashboardDTO;
import com.example.smart_wallet.modules.dashboard.application.dto.GetDashboardExpenseDTO;
import com.example.smart_wallet.modules.dashboard.application.dto.GetNonCreditExpensesDTO;
import com.example.smart_wallet.modules.dashboard.application.port.out.DashboardRepository;
import com.example.smart_wallet.shared.MoneyNormalizer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetDashboardUseCaseHandler implements GetDashboardUseCase {
    private final DashboardRepository dashboardRepository;
    private final FindCardIdsByWalletUseCase findCardIdsByWalletUseCase;

    public GetDashboardDTO execute(UUID walletId, Integer year, Integer month) {
        BigDecimal sumOfExpenses = BigDecimal.ZERO;
        List<UUID> cardIds = findCardIdsByWalletUseCase.execute(walletId);
        List<GetCreditCardStatementsDTO> statements = dashboardRepository.findStatementsByCards(year,
                month, cardIds);
        List<GetNonCreditExpensesDTO> recurrentExpenses = dashboardRepository.findNonCreditRecurrentExpenses();
        List<GetNonCreditExpensesDTO> payInFullExpenses = dashboardRepository.findPayInFullExpensesByMonthAndYear(year,
                month);
        List<GetDashboardExpenseDTO> dashboardExpenses = new ArrayList<>();

        for (GetCreditCardStatementsDTO statement : statements) {
            GetDashboardExpenseDTO dashboardExpense = new GetDashboardExpenseDTO(
                    statement.getCardId(),
                    statement.getCardName(),
                    MoneyNormalizer.centsToReais(statement.getTotalStatement())
            );
            sumOfExpenses = sumOfExpenses.add(statement.getTotalStatement());
            dashboardExpenses.add(dashboardExpense);
        }

        for (GetNonCreditExpensesDTO expense : recurrentExpenses) {
            GetDashboardExpenseDTO dashboardExpense = new GetDashboardExpenseDTO(
                    expense.getExpenseId(),
                    expense.getExpenseDescription(),
                    MoneyNormalizer.centsToReais(expense.getExpenseCost())
            );

            sumOfExpenses = sumOfExpenses.add(expense.getExpenseCost());
            dashboardExpenses.add(dashboardExpense);
        }

        for (GetNonCreditExpensesDTO payInFullExpense : payInFullExpenses) {
            GetDashboardExpenseDTO dashboardExpense = new GetDashboardExpenseDTO(
                    payInFullExpense.getExpenseId(),
                    payInFullExpense.getExpenseDescription(),
                    MoneyNormalizer.centsToReais(payInFullExpense.getExpenseCost())
            );

            sumOfExpenses = sumOfExpenses.add(payInFullExpense.getExpenseCost());
            dashboardExpenses.add(dashboardExpense);
        }

        return new GetDashboardDTO(
                MoneyNormalizer.centsToReais(sumOfExpenses),
                dashboardExpenses
        );
    }
}
