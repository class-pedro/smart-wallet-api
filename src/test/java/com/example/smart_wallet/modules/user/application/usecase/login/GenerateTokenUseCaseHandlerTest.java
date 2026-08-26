package com.example.smart_wallet.modules.user.application.usecase.login;

import com.example.smart_wallet.modules.user.application.port.out.TokenGenerator;
import com.example.smart_wallet.modules.user.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateTokenUseCaseHandlerTest {

    @Mock
    private TokenGenerator tokenGenerator;

    @InjectMocks
    private GenerateTokenUseCaseHandler handler;

    @Test
    void delegatesToTokenGenerator() {
        User user = new User();
        when(tokenGenerator.generateToken(user)).thenReturn("token-123");

        String result = handler.execute(user);

        assertThat(result).isEqualTo("token-123");
        verify(tokenGenerator).generateToken(user);
    }
}
