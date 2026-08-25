package com.example.smart_wallet.modules.expense.application.usecase.create;

import com.example.smart_wallet.modules.card.application.usecase.find.FindCardUseCase;
import com.example.smart_wallet.modules.card.domain.entity.Card;
import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.application.mapper.CreateExpenseMapper;
import com.example.smart_wallet.modules.expense.application.port.out.ExpenseRepository;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;
import com.example.smart_wallet.modules.wallet.application.usecase.find.FindWalletUseCase;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateExpenseUseCaseHandler implements CreateExpenseUseCase {
    private final ExpenseRepository expenseRepository;
    private final FindWalletUseCase findWalletUseCase;
    private final FindCardUseCase findCardUseCase;

    @Override
    public Expense execute(CreateExpenseCommand createExpenseCommand) {

        validateExpense(createExpenseCommand.paymentType(), createExpenseCommand.walletId(), createExpenseCommand.cardId());

        Card card = null;
        Wallet wallet = null;
        final Expense expense = CreateExpenseMapper.toEntity(createExpenseCommand);

        if ((createExpenseCommand.paymentType().equals("credit") ||
                createExpenseCommand.paymentType().equals("debit")) &&
                createExpenseCommand.cardId() != null) {
            card = findCardUseCase.execute(createExpenseCommand.cardId());

            if (card == null) {
                throw new IllegalArgumentException("Card not found");
            }

            expense.setCard(card);
        }

        if (createExpenseCommand.paymentType().equals("money") &&
                createExpenseCommand.walletId() != null) {
            wallet = findWalletUseCase.execute(createExpenseCommand.walletId());

            if (wallet == null) {
                throw new IllegalArgumentException("Wallet not found");
            }

            expense.setWallet(wallet);
        }

        Expense newExpense = expenseRepository.save(expense);

        if (createExpenseCommand.installments() != null && createExpenseCommand.installments() > 1) {
            registerInstallments(newExpense.getId(), createExpenseCommand, wallet, card);
        }

        return newExpense;
    }

    private void registerInstallments(UUID rootExpenseId,
                                      CreateExpenseCommand createExpenseCommand,
                                      Wallet wallet,
                                      Card card) {
        final Integer quantityOfInstallments = createExpenseCommand.installments();
        final BigDecimal rootExpenseCost = createExpenseCommand.cost();
        final LocalDateTime rootExpensePurchaseDate = createExpenseCommand.purchaseDate();

        BigDecimal installmentCost = rootExpenseCost
                .divide(BigDecimal.valueOf(quantityOfInstallments), 2, RoundingMode.HALF_UP);
        List<Expense> installments = new ArrayList<>();

        for (int installmentNumber = 1; installmentNumber <= quantityOfInstallments; installmentNumber++) {
            final Expense expense = CreateExpenseMapper.toEntity(createExpenseCommand);

            expense.setDescription(createExpenseCommand.description() + " " + installmentNumber + "/" + createExpenseCommand.installments());
            expense.setWallet(wallet);
            expense.setCard(card);
            expense.setPurchaseDate(calculateInstallmentDate(rootExpensePurchaseDate, installmentNumber));
            expense.setInstallments(null);
            expense.setInstallmentNumber(installmentNumber);
            expense.setRootExpense(String.valueOf(rootExpenseId));

            if (installmentNumber == quantityOfInstallments) {
                BigDecimal sumOfPreviousInstallmentCosts =
                        installmentCost.multiply(BigDecimal.valueOf(quantityOfInstallments - 1));
                installmentCost = rootExpenseCost.subtract(sumOfPreviousInstallmentCosts);
            }

            expense.setCost(installmentCost.setScale(2, RoundingMode.HALF_UP));

            installments.add(expense);
        }

        expenseRepository.saveAll(installments);
    }

    private LocalDateTime calculateInstallmentDate(LocalDateTime purchaseDateTime, int installmentNum) {
        LocalDate provisionalDate = purchaseDateTime.toLocalDate().plusMonths(installmentNum - 1);

        int day = purchaseDateTime.getDayOfMonth();
        int lastDayOfMonth = provisionalDate.lengthOfMonth();

        if (day > lastDayOfMonth) {
            day = lastDayOfMonth;
        }

        return provisionalDate.withDayOfMonth(day)
                .atTime(purchaseDateTime.getHour(), purchaseDateTime.getMinute(), purchaseDateTime.getSecond());
    }

    private void validateExpense(String paymentType, String walletId, String cardId) {
        final boolean hasWalletId = walletId != null;
        final boolean hasCardId = cardId != null;
        validateExpenseDestination(hasWalletId, hasCardId);
        validateExpenseOrigin(paymentType, hasWalletId, hasCardId);

    }

    private void validateExpenseDestination(boolean hasWalletId, boolean hasCardId) {
        if (hasWalletId && hasCardId) {
            throw new IllegalArgumentException("Only one of valid cardId and walletId can be provided");
        }

        if (!hasWalletId && !hasCardId) {
            throw new IllegalArgumentException("walletId and cardId cannot be null");
        }
    }

    // TO DO PaymentType deve ser tipado com o enum
    private void validateExpenseOrigin(String paymentType, boolean hasWalletId, boolean hasCardId) {
        boolean isMoneyPaymentType = paymentType.equals("money");
        boolean isCardPaymentType = paymentType.equals("credit") || paymentType.equals("debit");

        if (!isMoneyPaymentType && !isCardPaymentType) {
            throw new IllegalArgumentException("paymentType inválido: " + paymentType);
        }

        if (isMoneyPaymentType && (!hasWalletId || hasCardId)) {
            throw new IllegalArgumentException("In 'money' paymentType, walletId must be provided and cardId " +
                    "must be null");
        }

        if (isCardPaymentType && (!hasCardId || hasWalletId)) {
            throw new IllegalArgumentException("In 'card' paymentType, cardId must be provided and walletId " +
                    "must be null.");
        }
    }

}
