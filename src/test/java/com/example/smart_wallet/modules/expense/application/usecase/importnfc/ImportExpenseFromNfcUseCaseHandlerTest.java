package com.example.smart_wallet.modules.expense.application.usecase.importnfc;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.application.dto.ImportExpenseFromNfcCommand;
import com.example.smart_wallet.modules.expense.application.port.out.ExpenseNfcMetadataRepository;
import com.example.smart_wallet.modules.expense.application.port.out.NfcContentFetcher;
import com.example.smart_wallet.modules.expense.application.port.out.NfcExpenseParser;
import com.example.smart_wallet.modules.expense.application.usecase.create.CreateExpenseUseCase;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;
import com.example.smart_wallet.modules.expense.domain.entity.ExpenseNfcMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImportExpenseFromNfcUseCaseHandlerTest {

    @Mock
    private NfcContentFetcher nfcContentFetcher;

    @Mock
    private NfcExpenseParser nfcExpenseParser;

    @Mock
    private CreateExpenseUseCase createExpenseUseCase;

    @Mock
    private ExpenseNfcMetadataRepository expenseNfcMetadataRepository;

    @InjectMocks
    private ImportExpenseFromNfcUseCaseHandler handler;

    @Test
    void rejectsUrlsFromDisallowedHosts() {
        ImportExpenseFromNfcCommand command = new ImportExpenseFromNfcCommand(
                "https://malicious.example.com/nfce", "credit", null, "card-1");

        assertThrows(IllegalArgumentException.class, () -> handler.execute(command));
        verifyNoInteractions(nfcContentFetcher);
    }

    @Test
    void importsExpenseFromAnAllowedHost() {
        ImportExpenseFromNfcCommand command = new ImportExpenseFromNfcCommand(
                "https://portalsped.fazenda.mg.gov.br/nfce", "credit", null, "card-1");
        String html = "<html></html>";
        when(nfcContentFetcher.fetch(command.url())).thenReturn(html);

        CreateExpenseCommand parsedExpense = new CreateExpenseCommand(
                "Loja", new BigDecimal("50.00"), "credit", "payInFull",
                null, null, null, "pending", null, null);
        when(nfcExpenseParser.parseExpense(html)).thenReturn(parsedExpense);

        ExpenseNfcMetadata metadata = ExpenseNfcMetadata.builder().rawHtml(html).build();
        when(nfcExpenseParser.parseItems(html)).thenReturn(metadata);

        Expense newExpense = new Expense();
        newExpense.setId(UUID.randomUUID());
        when(createExpenseUseCase.execute(any(CreateExpenseCommand.class))).thenReturn(newExpense);

        CreateExpenseCommand result = handler.execute(command);

        assertThat(result.walletId()).isNull();
        assertThat(result.cardId()).isEqualTo("card-1");
        assertThat(result.description()).isEqualTo("Loja");

        ArgumentCaptor<CreateExpenseCommand> captor = ArgumentCaptor.forClass(CreateExpenseCommand.class);
        verify(createExpenseUseCase).execute(captor.capture());
        assertThat(captor.getValue().cardId()).isEqualTo("card-1");

        assertThat(metadata.getExpense()).isEqualTo(newExpense);
        verify(expenseNfcMetadataRepository).save(metadata);
    }
}
