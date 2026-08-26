package com.example.smart_wallet.modules.user.application.usecase.signup;

import com.example.smart_wallet.modules.user.application.dto.CreateUserCommand;
import com.example.smart_wallet.modules.user.application.port.out.UserRepository;
import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.wallet.application.usecase.create.CreateWalletUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignUpUserUseCaseHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreateWalletUseCase createWalletUseCase;

    @InjectMocks
    private SignUpUserUseCaseHandler handler;

    @Test
    void encryptsPasswordBeforeSavingAndCreatesAWallet() {
        CreateUserCommand command = new CreateUserCommand(
                "Pedro", "pedro@example.com", "11987654321", "11144477735", "PlainPassword1!");
        User savedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        handler.execute(command);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User userToSave = captor.getValue();
        assertThat(userToSave.getName()).isEqualTo("Pedro");
        assertThat(userToSave.getEmail()).isEqualTo("pedro@example.com");
        assertThat(userToSave.getPasswordHash()).isNotEqualTo("PlainPassword1!");
        assertThat(new BCryptPasswordEncoder().matches("PlainPassword1!", userToSave.getPasswordHash())).isTrue();

        verify(createWalletUseCase).execute(savedUser);
    }
}
