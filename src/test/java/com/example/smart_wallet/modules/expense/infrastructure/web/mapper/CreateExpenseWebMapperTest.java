package com.example.smart_wallet.modules.expense.infrastructure.web.mapper;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.infrastructure.web.dto.CreateExpenseRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CreateExpenseWebMapperTest {

    @Test
    void mapsRequestFieldsToCommand() {
        LocalDateTime paymentDate = LocalDateTime.of(2026, 1, 10, 12, 0);
        LocalDateTime purchaseDate = LocalDateTime.of(2026, 1, 5, 9, 30);
        CreateExpenseRequest request = new CreateExpenseRequest(
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

        CreateExpenseCommand command = CreateExpenseWebMapper.toCommand(request);

        assertThat(command.description()).isEqualTo("Mercado");
        assertThat(command.cost()).isEqualByComparingTo("150.00");
        assertThat(command.paymentType()).isEqualTo("credit");
        assertThat(command.paymentMethod()).isEqualTo("installment");
        assertThat(command.paymentDate()).isEqualTo(paymentDate);
        assertThat(command.purchaseDate()).isEqualTo(purchaseDate);
        assertThat(command.installments()).isEqualTo(3);
        assertThat(command.status()).isEqualTo("pending");
        assertThat(command.walletId()).isNull();
        assertThat(command.cardId()).isEqualTo("card-1");
    }
}
