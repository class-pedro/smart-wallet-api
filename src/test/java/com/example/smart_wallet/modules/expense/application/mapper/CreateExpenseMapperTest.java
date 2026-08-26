package com.example.smart_wallet.modules.expense.application.mapper;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CreateExpenseMapperTest {

    @Test
    void mapsCommandFieldsToEntity() {
        LocalDateTime paymentDate = LocalDateTime.of(2026, 1, 10, 12, 0);
        LocalDateTime purchaseDate = LocalDateTime.of(2026, 1, 5, 9, 30);
        CreateExpenseCommand command = new CreateExpenseCommand(
                "Mercado",
                new BigDecimal("150.00"),
                "credit",
                "installment",
                paymentDate,
                purchaseDate,
                3,
                "pending",
                null,
                "card-1"
        );

        Expense expense = CreateExpenseMapper.toEntity(command);

        assertThat(expense.getDescription()).isEqualTo("Mercado");
        assertThat(expense.getCost()).isEqualByComparingTo("150.00");
        assertThat(expense.getPaymentType()).isEqualTo("credit");
        assertThat(expense.getPaymentMethod()).isEqualTo("installment");
        assertThat(expense.getPaymentDate()).isEqualTo(paymentDate);
        assertThat(expense.getPurchaseDate()).isEqualTo(purchaseDate);
        assertThat(expense.getInstallments()).isEqualTo(3);
        assertThat(expense.getStatus()).isEqualTo("pending");
        assertThat(expense.getWallet()).isNull();
        assertThat(expense.getCard()).isNull();
    }
}
