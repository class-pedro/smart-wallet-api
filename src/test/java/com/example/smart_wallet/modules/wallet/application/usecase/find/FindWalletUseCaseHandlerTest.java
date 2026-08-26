package com.example.smart_wallet.modules.wallet.application.usecase.find;

import com.example.smart_wallet.modules.wallet.application.port.out.WalletRepository;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
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
class FindWalletUseCaseHandlerTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private FindWalletUseCaseHandler handler;

    @Test
    void returnsNullWhenWalletIdIsNull() {
        Wallet result = handler.execute(null);

        assertThat(result).isNull();
        verifyNoInteractions(walletRepository);
    }

    @Test
    void returnsWalletWhenFound() {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        Wallet result = handler.execute(walletId.toString());

        assertThat(result).isEqualTo(wallet);
    }

    @Test
    void returnsNullWhenNotFound() {
        UUID walletId = UUID.randomUUID();
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        Wallet result = handler.execute(walletId.toString());

        assertThat(result).isNull();
    }
}
