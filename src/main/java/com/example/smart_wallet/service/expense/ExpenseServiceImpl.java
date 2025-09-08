package com.example.smart_wallet.service.expense;

import com.example.smart_wallet.domain.entity.Card;
import com.example.smart_wallet.domain.entity.Expense;
import com.example.smart_wallet.domain.entity.Wallet;
import com.example.smart_wallet.dto.CreateExpenseDTO;
import com.example.smart_wallet.infrastructure.repository.ExpenseRepository.ExpenseRepository;
import com.example.smart_wallet.mapper.CreateExpenseMapper;
import com.example.smart_wallet.service.card.CardService;
import com.example.smart_wallet.service.wallet.WalletService;
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
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final WalletService walletService;
    private final CardService cardService;

    @Override
    public void create(CreateExpenseDTO createExpenseDTO) {

        validateExpense(createExpenseDTO.getPaymentType(), createExpenseDTO.getWalletId(), createExpenseDTO.getCardId());

        Card card = null;
        Wallet wallet = null;
        final Expense expense = CreateExpenseMapper.toEntity(createExpenseDTO);

        if ((createExpenseDTO.getPaymentType().equals("credit") ||
                createExpenseDTO.getPaymentType().equals("debit")) &&
                createExpenseDTO.getCardId() != null) {
            card = cardService.getById(createExpenseDTO.getCardId());

            if (card == null) {
                throw new IllegalArgumentException("Card not found");
            }

            expense.setCard(card);
        }

        if (createExpenseDTO.getPaymentType().equals("money") &&
                createExpenseDTO.getWalletId() != null) {
            wallet = walletService.getById(createExpenseDTO.getWalletId());

            if (wallet == null) {
                throw new IllegalArgumentException("Wallet not found");
            }

            expense.setWallet(wallet);
        }

        UUID newExpenseId = expenseRepository.save(expense).getId();

        if (createExpenseDTO.getInstallments() != null && createExpenseDTO.getInstallments() > 1) {
            registerInstallments(newExpenseId, createExpenseDTO, wallet, card);
        }
    }

    private void registerInstallments(UUID rootExpenseId,
                                      CreateExpenseDTO createExpenseDTO,
                                      Wallet wallet,
                                      Card card) {
        final Integer quantityOfInstallments = createExpenseDTO.getInstallments();
        final BigDecimal rootExpenseCost = createExpenseDTO.getCost();
        final LocalDateTime rootExpensePurchaseDate = createExpenseDTO.getPurchaseDate();

        BigDecimal installmentCost = rootExpenseCost
                .divide(BigDecimal.valueOf(quantityOfInstallments), 2, RoundingMode.HALF_UP);
        List<Expense> installments = new ArrayList<>();

        for (int installmentNumber = 1; installmentNumber <= quantityOfInstallments; installmentNumber++) {
            final Expense expense = CreateExpenseMapper.toEntity(createExpenseDTO);

            expense.setDescription(createExpenseDTO.getDescription() + " " + installmentNumber + "/" + createExpenseDTO.getInstallments());
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
