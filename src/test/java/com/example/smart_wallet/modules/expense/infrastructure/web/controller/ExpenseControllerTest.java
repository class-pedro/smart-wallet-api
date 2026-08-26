package com.example.smart_wallet.modules.expense.infrastructure.web.controller;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.application.usecase.create.CreateExpenseUseCase;
import com.example.smart_wallet.modules.expense.application.usecase.importnfc.ImportExpenseFromNfcUseCase;
import com.example.smart_wallet.modules.expense.infrastructure.web.dto.CreateExpenseRequest;
import com.example.smart_wallet.modules.expense.infrastructure.web.dto.ImportExpenseFromNfcRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @Mock
    private CreateExpenseUseCase createExpenseUseCase;

    @Mock
    private ImportExpenseFromNfcUseCase importExpenseFromNfcUseCase;

    @InjectMocks
    private ExpenseController controller;

    @Test
    void createExpenseMapsRequestAndDelegatesToUseCase() {
        CreateExpenseRequest request = new CreateExpenseRequest(
                "Mercado", new BigDecimal("100.00"), "money", "payInFull",
                null, LocalDateTime.now(), null, "pending", "wallet-1", null);

        controller.createExpense(request);

        ArgumentCaptor<CreateExpenseCommand> captor = ArgumentCaptor.forClass(CreateExpenseCommand.class);
        verify(createExpenseUseCase).execute(captor.capture());
        assertThat(captor.getValue().description()).isEqualTo("Mercado");
        assertThat(captor.getValue().walletId()).isEqualTo("wallet-1");
    }

    @Test
    void createExpenseFromNfcReturnsTheParsedCommand() {
        ImportExpenseFromNfcRequest request = new ImportExpenseFromNfcRequest(
                "https://portalsped.fazenda.mg.gov.br/nfce", "credit", null, "card-1");
        CreateExpenseCommand parsed = new CreateExpenseCommand(
                "Loja", new BigDecimal("50.00"), "credit", "payInFull",
                null, null, null, "pending", null, "card-1");
        when(importExpenseFromNfcUseCase.execute(org.mockito.ArgumentMatchers.any())).thenReturn(parsed);

        ResponseEntity<CreateExpenseCommand> response = controller.createExpenseFromNfc(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(parsed);
    }
}
