package com.example.smart_wallet.modules.expense.application.usecase.create;

import com.example.smart_wallet.modules.card.application.usecase.find.FindCardUseCase;
import com.example.smart_wallet.modules.card.domain.entity.Card;
import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.application.port.out.ExpenseRepository;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;
import com.example.smart_wallet.modules.wallet.application.usecase.find.FindWalletUseCase;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateExpenseUseCaseHandlerTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private FindWalletUseCase findWalletUseCase;

    @Mock
    private FindCardUseCase findCardUseCase;

    @InjectMocks
    private CreateExpenseUseCaseHandler handler;

    private CreateExpenseCommand command(String paymentType, Integer installments, String walletId, String cardId) {
        return new CreateExpenseCommand(
                "Mercado",
                new BigDecimal("100.00"),
                paymentType,
                "payInFull",
                null,
                LocalDateTime.of(2026, 1, 15, 10, 0),
                installments,
                "pending",
                walletId,
                cardId
        );
    }

    @Test
    void rejectsWhenBothWalletAndCardAreProvided() {
        assertThrows(IllegalArgumentException.class,
                () -> handler.execute(command("money", null, "wallet-1", "card-1")));
        verifyNoInteractions(expenseRepository);
    }

    @Test
    void rejectsWhenNeitherWalletNorCardAreProvided() {
        assertThrows(IllegalArgumentException.class,
                () -> handler.execute(command("money", null, null, null)));
    }

    @Test
    void rejectsUnknownPaymentType() {
        assertThrows(IllegalArgumentException.class,
                () -> handler.execute(command("pix", null, "wallet-1", null)));
    }

    @Test
    void rejectsMoneyPaymentTypeWithoutWalletId() {
        assertThrows(IllegalArgumentException.class,
                () -> handler.execute(command("money", null, null, "card-1")));
    }

    @Test
    void rejectsCardPaymentTypeWithoutCardId() {
        assertThrows(IllegalArgumentException.class,
                () -> handler.execute(command("credit", null, "wallet-1", null)));
    }

    @Test
    void createsAMoneyExpenseLinkedToWallet() {
        Wallet wallet = new Wallet();
        when(findWalletUseCase.execute("wallet-1")).thenReturn(wallet);
        Expense saved = new Expense();
        saved.setId(UUID.randomUUID());
        when(expenseRepository.save(any(Expense.class))).thenReturn(saved);

        Expense result = handler.execute(command("money", null, "wallet-1", null));

        assertThat(result).isEqualTo(saved);
        ArgumentCaptor<Expense> captor = ArgumentCaptor.forClass(Expense.class);
        verify(expenseRepository).save(captor.capture());
        assertThat(captor.getValue().getWallet()).isEqualTo(wallet);
    }

    @Test
    void rejectsMoneyExpenseWhenWalletIsNotFound() {
        when(findWalletUseCase.execute("wallet-1")).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> handler.execute(command("money", null, "wallet-1", null)));
    }

    @Test
    void createsACreditExpenseLinkedToCard() {
        Card card = new Card();
        when(findCardUseCase.execute("card-1")).thenReturn(card);
        Expense saved = new Expense();
        saved.setId(UUID.randomUUID());
        when(expenseRepository.save(any(Expense.class))).thenReturn(saved);

        handler.execute(command("credit", null, null, "card-1"));

        ArgumentCaptor<Expense> captor = ArgumentCaptor.forClass(Expense.class);
        verify(expenseRepository).save(captor.capture());
        assertThat(captor.getValue().getCard()).isEqualTo(card);
        verify(expenseRepository, never()).saveAll(any());
    }

    @Test
    void rejectsCreditExpenseWhenCardIsNotFound() {
        when(findCardUseCase.execute("card-1")).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> handler.execute(command("credit", null, null, "card-1")));
    }

    @Test
    void registersInstallmentsWhenMoreThanOne() {
        Card card = new Card();
        when(findCardUseCase.execute("card-1")).thenReturn(card);
        UUID rootId = UUID.randomUUID();
        Expense saved = new Expense();
        saved.setId(rootId);
        when(expenseRepository.save(any(Expense.class))).thenReturn(saved);

        handler.execute(command("credit", 3, null, "card-1"));

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Iterable<Expense>> captor = ArgumentCaptor.forClass(Iterable.class);
        verify(expenseRepository).saveAll(captor.capture());

        List<Expense> installments = toList(captor.getValue());
        assertThat(installments).hasSize(3);
        assertThat(installments.get(0).getDescription()).isEqualTo("Mercado 1/3");
        assertThat(installments.get(0).getInstallmentNumber()).isEqualTo(1);
        assertThat(installments.get(0).getRootExpense()).isEqualTo(String.valueOf(rootId));
        assertThat(installments.get(0).getCost()).isEqualByComparingTo("33.33");
        assertThat(installments.get(1).getCost()).isEqualByComparingTo("33.33");
        // Last installment absorbs the rounding remainder.
        assertThat(installments.get(2).getCost()).isEqualByComparingTo("33.34");
        assertThat(installments).allMatch(e -> e.getInstallments() == null);
        assertThat(installments).allMatch(e -> e.getCard() == card);
    }

    @Test
    void clampsInstallmentDayToTheLastDayOfShorterMonths() {
        Card card = new Card();
        when(findCardUseCase.execute("card-1")).thenReturn(card);
        Expense saved = new Expense();
        saved.setId(UUID.randomUUID());
        when(expenseRepository.save(any(Expense.class))).thenReturn(saved);

        CreateExpenseCommand cmd = new CreateExpenseCommand(
                "Assinatura",
                new BigDecimal("60.00"),
                "credit",
                "installment",
                null,
                LocalDateTime.of(2026, 1, 31, 8, 0),
                2,
                "pending",
                null,
                "card-1"
        );

        handler.execute(cmd);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Iterable<Expense>> captor = ArgumentCaptor.forClass(Iterable.class);
        verify(expenseRepository).saveAll(captor.capture());
        List<Expense> installments = toList(captor.getValue());

        assertThat(installments.get(0).getPurchaseDate()).isEqualTo(LocalDateTime.of(2026, 1, 31, 8, 0));
        // February 2026 has 28 days, so the day is clamped.
        assertThat(installments.get(1).getPurchaseDate()).isEqualTo(LocalDateTime.of(2026, 2, 28, 8, 0));
    }

    private static List<Expense> toList(Iterable<Expense> iterable) {
        return java.util.stream.StreamSupport.stream(iterable.spliterator(), false).toList();
    }
}
