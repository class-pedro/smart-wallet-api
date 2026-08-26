package com.example.smart_wallet.modules.card.infrastructure.web.mapper;

import com.example.smart_wallet.modules.card.application.dto.CreateCardCommand;
import com.example.smart_wallet.modules.card.infrastructure.web.dto.CreateCardRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCardWebMapperTest {

    @Test
    void mapsRequestFieldsToCommand() {
        UUID cardTypeId = UUID.randomUUID();
        UUID walletId = UUID.randomUUID();
        CreateCardRequest request = new CreateCardRequest(
                "Nubank", 500, null, null, null, cardTypeId, walletId
        );

        CreateCardCommand command = CreateCardWebMapper.toCommand(request);

        assertThat(command.name()).isEqualTo("Nubank");
        assertThat(command.balance()).isEqualTo(500);
        assertThat(command.dueDateDay()).isNull();
        assertThat(command.closingDateDay()).isNull();
        assertThat(command.creditLimit()).isNull();
        assertThat(command.cardTypeId()).isEqualTo(cardTypeId);
        assertThat(command.walletId()).isEqualTo(walletId);
    }
}
