package com.example.smart_wallet.modules.card.application.usecase.cardtype;

import com.example.smart_wallet.modules.card.application.port.out.CardTypeRepository;
import com.example.smart_wallet.modules.card.domain.entity.CardType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCardTypesUseCaseHandlerTest {

    @Mock
    private CardTypeRepository cardTypeRepository;

    @InjectMocks
    private ListCardTypesUseCaseHandler handler;

    @Test
    void returnsAllCardTypesFromRepository() {
        CardType credit = new CardType();
        credit.setTitle("credit");
        List<CardType> cardTypes = List.of(credit);
        when(cardTypeRepository.findAll()).thenReturn(cardTypes);

        List<CardType> result = handler.execute();

        assertThat(result).isEqualTo(cardTypes);
        Mockito.verify(cardTypeRepository).findAll();
    }
}
