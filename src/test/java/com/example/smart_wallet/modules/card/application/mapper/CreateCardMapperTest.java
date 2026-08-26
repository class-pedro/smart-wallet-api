package com.example.smart_wallet.modules.card.application.mapper;

import com.example.smart_wallet.modules.card.application.dto.CreateCardCommand;
import com.example.smart_wallet.modules.card.domain.entity.Card;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCardMapperTest {

    @Test
    void mapsCommandFieldsToEntity() {
        CreateCardCommand command = new CreateCardCommand(
                "Nubank",
                null,
                10,
                5,
                1000,
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        Card card = CreateCardMapper.toEntity(command);

        assertThat(card.getName()).isEqualTo("Nubank");
        assertThat(card.getBalance()).isNull();
        assertThat(card.getDueDateDay()).isEqualTo(10);
        assertThat(card.getClosingDateDay()).isEqualTo(5);
        assertThat(card.getCreditLimit()).isEqualTo(1000);
        assertThat(card.getWallet()).isNull();
        assertThat(card.getCardType()).isNull();
    }
}
