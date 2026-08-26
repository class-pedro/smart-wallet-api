package com.example.smart_wallet.modules.expense.infrastructure.web.mapper;

import com.example.smart_wallet.modules.expense.application.dto.ImportExpenseFromNfcCommand;
import com.example.smart_wallet.modules.expense.infrastructure.web.dto.ImportExpenseFromNfcRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImportExpenseFromNfcWebMapperTest {

    @Test
    void mapsRequestFieldsToCommand() {
        ImportExpenseFromNfcRequest request = new ImportExpenseFromNfcRequest(
                "https://portalsped.fazenda.mg.gov.br/nfce",
                "credit",
                null,
                "card-1"
        );

        ImportExpenseFromNfcCommand command = ImportExpenseFromNfcWebMapper.toCommand(request);

        assertThat(command.url()).isEqualTo("https://portalsped.fazenda.mg.gov.br/nfce");
        assertThat(command.paymentType()).isEqualTo("credit");
        assertThat(command.walletId()).isNull();
        assertThat(command.cardId()).isEqualTo("card-1");
    }
}
