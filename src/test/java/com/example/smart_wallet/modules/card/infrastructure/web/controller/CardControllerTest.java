package com.example.smart_wallet.modules.card.infrastructure.web.controller;

import com.example.smart_wallet.modules.card.application.dto.GetCardIdAndNameDTO;
import com.example.smart_wallet.modules.card.application.usecase.create.CreateCardUseCase;
import com.example.smart_wallet.modules.card.application.usecase.find.FindCardIdsAndNameByWalletUseCase;
import com.example.smart_wallet.modules.card.infrastructure.web.dto.CreateCardRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @Mock
    private CreateCardUseCase createCardUseCase;

    @Mock
    private FindCardIdsAndNameByWalletUseCase findCardIdsAndNameByWalletUseCase;

    @InjectMocks
    private CardController controller;

    @Test
    void createCardMapsRequestAndReturns201() {
        UUID cardTypeId = UUID.randomUUID();
        UUID walletId = UUID.randomUUID();
        CreateCardRequest request = new CreateCardRequest("Nubank", null, 10, 5, 1000, cardTypeId, walletId);

        ResponseEntity<String> response = controller.createCard(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var captor = ArgumentCaptor.forClass(com.example.smart_wallet.modules.card.application.dto.CreateCardCommand.class);
        verify(createCardUseCase).execute(captor.capture());
        assertThat(captor.getValue().name()).isEqualTo("Nubank");
        assertThat(captor.getValue().cardTypeId()).isEqualTo(cardTypeId);
        assertThat(captor.getValue().walletId()).isEqualTo(walletId);
    }

    @Test
    void getCardIdAndNameByIdReturnsUseCaseResult() {
        String walletId = UUID.randomUUID().toString();
        List<GetCardIdAndNameDTO> expected = List.of(new GetCardIdAndNameDTO(UUID.randomUUID(), "Nubank"));
        when(findCardIdsAndNameByWalletUseCase.execute(walletId)).thenReturn(expected);

        ResponseEntity<List<GetCardIdAndNameDTO>> response = controller.getCardIdAndNameById(walletId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }
}
