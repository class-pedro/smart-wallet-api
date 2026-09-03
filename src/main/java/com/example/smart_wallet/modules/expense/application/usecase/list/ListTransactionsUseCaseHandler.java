package com.example.smart_wallet.modules.expense.application.usecase.list;

import com.example.smart_wallet.modules.dashboard.application.usecase.dashboard.GetDashboardUseCase;
import com.example.smart_wallet.modules.expense.application.dto.GetExpenseListItemDTO;
import com.example.smart_wallet.modules.expense.application.dto.GetTransactionsDTO;
import com.example.smart_wallet.modules.expense.application.dto.GetTransactionsSummaryDTO;
import com.example.smart_wallet.modules.expense.application.port.out.ExpenseRepository;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;
import com.example.smart_wallet.modules.wallet.application.usecase.find.FindWalletUseCase;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import com.example.smart_wallet.shared.MoneyNormalizer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ListTransactionsUseCaseHandler implements ListTransactionsUseCase {
    private final ExpenseRepository expenseRepository;
    private final FindWalletUseCase findWalletUseCase;
    private final GetDashboardUseCase getDashboardUseCase;

    @Override
    public GetTransactionsDTO execute(String walletId) {
        UUID walletUUID = UUID.fromString(walletId);
        Wallet wallet = findWalletUseCase.execute(walletId);

        LocalDate now = LocalDate.now();
        BigDecimal monthExpenses = getDashboardUseCase.execute(walletUUID, now.getYear(), now.getMonthValue())
                .getTotal();

        BigDecimal currentBalance = wallet == null || wallet.getBalance() == null
                ? BigDecimal.ZERO
                : MoneyNormalizer.centsToReais(BigDecimal.valueOf(wallet.getBalance()));

        GetTransactionsSummaryDTO summary = new GetTransactionsSummaryDTO(
                currentBalance,
                monthExpenses,
                BigDecimal.ZERO
        );

        List<Expense> expenses = expenseRepository.findTransactionsByWalletId(walletUUID);

        List<GetExpenseListItemDTO> transactions = expenses.stream()
                .map(this::toListItem)
                .toList();

        return new GetTransactionsDTO(summary, transactions);
    }

    private GetExpenseListItemDTO toListItem(Expense expense) {
        return new GetExpenseListItemDTO(
                expense.getId(),
                expense.getPurchaseDate(),
                expense.getDescription(),
                expense.getPaymentType(),
                expense.getPaymentMethod(),
                expense.getInstallmentNumber(),
                expense.getInstallments(),
                MoneyNormalizer.centsToReais(expense.getCost()),
                expense.getStatus()
        );
    }
}
