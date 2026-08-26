package com.example.smart_wallet.modules.card.application.usecase.find;

import com.example.smart_wallet.modules.card.application.dto.GetCardIdAndNameDTO;
import com.example.smart_wallet.modules.card.application.port.out.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCardIdsAndNameByWalletUseCaseHandlerTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private FindCardIdsAndNameByWalletUseCaseHandler handler;

    @Test
    void parsesWalletIdAndDelegatesToRepository() {
        UUID walletId = UUID.randomUUID();
        List<GetCardIdAndNameDTO> expected = List.of(new GetCardIdAndNameDTO(UUID.randomUUID(), "Nubank"));
        when(cardRepository.findIdsAndNameByWalletId(walletId)).thenReturn(expected);

        List<GetCardIdAndNameDTO> result = handler.execute(walletId.toString());

        assertThat(result).isEqualTo(expected);
        verify(cardRepository).findIdsAndNameByWalletId(walletId);
    }
}
