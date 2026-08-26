package com.example.smart_wallet.modules.card.infrastructure.web.controller;

import com.example.smart_wallet.modules.card.application.usecase.cardtype.ListCardTypesUseCase;
import com.example.smart_wallet.modules.card.domain.entity.CardType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardTypeControllerTest {

    @Mock
    private ListCardTypesUseCase listCardTypesUseCase;

    @InjectMocks
    private CardTypeController controller;

    @Test
    void returnsAllCardTypes() {
        CardType creditType = new CardType();
        creditType.setTitle("credit");
        List<CardType> expected = List.of(creditType);
        when(listCardTypesUseCase.execute()).thenReturn(expected);

        ResponseEntity<List<CardType>> response = controller.getCardIdAndNameById();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }
}
