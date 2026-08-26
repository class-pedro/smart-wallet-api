package com.example.smart_wallet.modules.user.application.usecase.completeProfile;

import com.example.smart_wallet.modules.user.application.dto.CompleteProfileCommand;
import com.example.smart_wallet.modules.user.application.port.out.TokenGenerator;
import com.example.smart_wallet.modules.user.application.port.out.UserRepository;
import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.wallet.application.usecase.create.CreateWalletUseCase;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompleteProfileUseCaseHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenGenerator tokenGenerator;

    @Mock
    private CreateWalletUseCase createWalletUseCase;

    @InjectMocks
    private CompleteProfileUseCaseHandler handler;

    @Test
    void fillsInTheProfileAndCreatesAWalletWhenTheUserHasNone() {
        User user = new User();
        user.setEmail("pedro@example.com");
        when(tokenGenerator.getEmailFromToken("Bearer abc")).thenReturn("pedro@example.com");
        when(userRepository.findEntityByEmail("pedro@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        when(createWalletUseCase.execute(user)).thenReturn(wallet);
        when(tokenGenerator.generateToken(user)).thenReturn("new-token");

        String token = handler.execute("Bearer abc", new CompleteProfileCommand("11144477735", "11987654321"));

        assertThat(token).isEqualTo("new-token");
        assertThat(user.getCpf()).isEqualTo("11144477735");
        assertThat(user.getCellphone()).isEqualTo("11987654321");
        assertThat(user.getWallet()).isEqualTo(wallet);
        verify(createWalletUseCase).execute(user);
    }

    @Test
    void doesNotCreateAWalletWhenTheUserAlreadyHasOne() {
        User user = new User();
        user.setEmail("pedro@example.com");
        user.setWallet(new Wallet());
        when(tokenGenerator.getEmailFromToken("Bearer abc")).thenReturn("pedro@example.com");
        when(userRepository.findEntityByEmail("pedro@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(tokenGenerator.generateToken(user)).thenReturn("new-token");

        handler.execute("Bearer abc", new CompleteProfileCommand("11144477735", "11987654321"));

        verify(createWalletUseCase, never()).execute(user);
    }

    @Test
    void throwsWhenTheUserFromTheTokenDoesNotExist() {
        when(tokenGenerator.getEmailFromToken("Bearer abc")).thenReturn("ghost@example.com");
        when(userRepository.findEntityByEmail("ghost@example.com")).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,
                () -> handler.execute("Bearer abc", new CompleteProfileCommand("11144477735", "11987654321")));
    }
}
