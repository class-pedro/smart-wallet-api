package com.example.smart_wallet.modules.card.application.usecase.find;

import com.example.smart_wallet.modules.card.application.port.out.CardRepository;
import com.example.smart_wallet.modules.card.domain.entity.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCardUseCaseHandlerTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private FindCardUseCaseHandler handler;

    @Test
    void returnsNullWhenCardIdIsNull() {
        Card result = handler.execute(null);

        assertThat(result).isNull();
        verifyNoInteractions(cardRepository);
    }

    @Test
    void returnsCardWhenFound() {
        UUID cardId = UUID.randomUUID();
        Card card = new Card();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        Card result = handler.execute(cardId.toString());

        assertThat(result).isEqualTo(card);
    }

    @Test
    void returnsNullWhenNotFound() {
        UUID cardId = UUID.randomUUID();
        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        Card result = handler.execute(cardId.toString());

        assertThat(result).isNull();
    }
}
