package com.example.smart_wallet.modules.user.application.usecase.me;

import com.example.smart_wallet.modules.user.application.port.out.TokenGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetWalletIdUseCaseHandlerTest {

    @Mock
    private TokenGenerator tokenGenerator;

    @InjectMocks
    private GetWalletIdUseCaseHandler handler;

    @Test
    void delegatesToTokenGenerator() {
        when(tokenGenerator.getWalletIdFromToken("Bearer abc")).thenReturn("wallet-123");

        String result = handler.execute("Bearer abc");

        assertThat(result).isEqualTo("wallet-123");
        verify(tokenGenerator).getWalletIdFromToken("Bearer abc");
    }
}
