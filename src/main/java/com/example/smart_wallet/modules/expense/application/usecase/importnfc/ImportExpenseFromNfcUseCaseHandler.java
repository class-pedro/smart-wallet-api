package com.example.smart_wallet.modules.expense.application.usecase.importnfc;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.application.dto.ImportExpenseFromNfcCommand;
import com.example.smart_wallet.modules.expense.application.port.out.ExpenseNfcMetadataRepository;
import com.example.smart_wallet.modules.expense.application.port.out.NfcContentFetcher;
import com.example.smart_wallet.modules.expense.application.port.out.NfcExpenseParser;
import com.example.smart_wallet.modules.expense.application.usecase.create.CreateExpenseUseCase;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;
import com.example.smart_wallet.modules.expense.domain.entity.ExpenseNfcMetadata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
@AllArgsConstructor
public class ImportExpenseFromNfcUseCaseHandler implements ImportExpenseFromNfcUseCase {

    private static final List<String> ALLOWED_HOSTS = List.of(
            "portalsped.fazenda.mg.gov.br"
    );

    private final NfcContentFetcher nfcContentFetcher;
    private final NfcExpenseParser nfcExpenseParser;
    private final CreateExpenseUseCase createExpenseUseCase;
    private final ExpenseNfcMetadataRepository expenseNfcMetadataRepository;

    @Override
    public CreateExpenseCommand execute(ImportExpenseFromNfcCommand importExpenseFromNfcCommand) {

        validateUrl(importExpenseFromNfcCommand.url());

        String html = nfcContentFetcher.fetch(importExpenseFromNfcCommand.url());

        CreateExpenseCommand parsedExpense = nfcExpenseParser.parseExpense(html);
        ExpenseNfcMetadata expenseNfcMetadata = nfcExpenseParser.parseItems(html);

        CreateExpenseCommand createExpenseCommand = new CreateExpenseCommand(
                parsedExpense.description(),
                parsedExpense.cost(),
                parsedExpense.paymentType(),
                parsedExpense.paymentMethod(),
                parsedExpense.paymentDate(),
                parsedExpense.purchaseDate(),
                parsedExpense.installments(),
                parsedExpense.status(),
                importExpenseFromNfcCommand.walletId(),
                importExpenseFromNfcCommand.cardId()
        );

        Expense newExpense = createExpenseUseCase.execute(createExpenseCommand);

        expenseNfcMetadata.setExpense(newExpense);
        expenseNfcMetadataRepository.save(expenseNfcMetadata);

        return createExpenseCommand;
    }

    private void validateUrl(String url) {
        String host = URI.create(url).getHost();

        if (!ALLOWED_HOSTS.contains(host)) {
            throw new IllegalArgumentException("Host inválido");
        }
    }
}
