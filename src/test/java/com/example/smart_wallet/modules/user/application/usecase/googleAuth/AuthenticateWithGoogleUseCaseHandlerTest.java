package com.example.smart_wallet.modules.user.application.usecase.googleAuth;

import com.example.smart_wallet.modules.user.application.dto.GoogleUserInfo;
import com.example.smart_wallet.modules.user.application.port.out.TokenGenerator;
import com.example.smart_wallet.modules.user.application.port.out.UserRepository;
import com.example.smart_wallet.modules.user.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateWithGoogleUseCaseHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenGenerator tokenGenerator;

    @InjectMocks
    private AuthenticateWithGoogleUseCaseHandler handler;

    private static final GoogleUserInfo GOOGLE_USER_INFO =
            new GoogleUserInfo("google-sub-123", "pedro@example.com", "Pedro");

    @Test
    void createsANewUserWhenNoAccountExistsForTheEmail() {
        when(userRepository.findEntityByEmail("pedro@example.com")).thenReturn(Optional.empty());
        User savedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(tokenGenerator.generateToken(savedUser)).thenReturn("token-123");

        String token = handler.execute(GOOGLE_USER_INFO);

        assertThat(token).isEqualTo("token-123");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User userToSave = captor.getValue();
        assertThat(userToSave.getName()).isEqualTo("Pedro");
        assertThat(userToSave.getEmail()).isEqualTo("pedro@example.com");
        assertThat(userToSave.getGoogleId()).isEqualTo("google-sub-123");
    }

    @Test
    void linksTheGoogleAccountToAnExistingUserWithoutAGoogleId() {
        User existingUser = new User();
        existingUser.setEmail("pedro@example.com");
        when(userRepository.findEntityByEmail("pedro@example.com")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(tokenGenerator.generateToken(existingUser)).thenReturn("token-456");

        String token = handler.execute(GOOGLE_USER_INFO);

        assertThat(token).isEqualTo("token-456");
        assertThat(existingUser.getGoogleId()).isEqualTo("google-sub-123");
        verify(userRepository).save(existingUser);
    }

    @Test
    void doesNotOverwriteOrPersistWhenTheUserAlreadyHasAGoogleId() {
        User existingUser = new User();
        existingUser.setEmail("pedro@example.com");
        existingUser.setGoogleId("already-linked-sub");
        when(userRepository.findEntityByEmail("pedro@example.com")).thenReturn(Optional.of(existingUser));
        when(tokenGenerator.generateToken(existingUser)).thenReturn("token-789");

        String token = handler.execute(GOOGLE_USER_INFO);

        assertThat(token).isEqualTo("token-789");
        assertThat(existingUser.getGoogleId()).isEqualTo("already-linked-sub");
        verify(userRepository, never()).save(any(User.class));
    }
}
