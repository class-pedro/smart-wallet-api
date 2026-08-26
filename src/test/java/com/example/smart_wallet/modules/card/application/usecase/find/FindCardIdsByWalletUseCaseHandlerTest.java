package com.example.smart_wallet.modules.card.application.usecase.find;

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
class FindCardIdsByWalletUseCaseHandlerTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private FindCardIdsByWalletUseCaseHandler handler;

    @Test
    void delegatesToRepository() {
        UUID walletId = UUID.randomUUID();
        List<UUID> expected = List.of(UUID.randomUUID(), UUID.randomUUID());
        when(cardRepository.findIdsByWalletId(walletId)).thenReturn(expected);

        List<UUID> result = handler.execute(walletId);

        assertThat(result).isEqualTo(expected);
        verify(cardRepository).findIdsByWalletId(walletId);
    }
}
