package com.example.smart_wallet.modules.user.application.usecase.signup;

import com.example.smart_wallet.modules.user.application.dto.CreateUserCommand;
import com.example.smart_wallet.modules.user.application.mapper.CreateUserMapper;
import com.example.smart_wallet.modules.user.application.port.out.UserRepository;
import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.wallet.application.usecase.create.CreateWalletUseCase;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignUpUserUseCaseHandler implements SignUpUserUseCase {
    private final UserRepository userRepository;
    private final CreateWalletUseCase createWalletUseCase;

    @Override
    public void execute(CreateUserCommand createUserCommand) {
        final User newUser = createUser(createUserCommand);

        createWalletUseCase.execute(newUser);
    }

    private User createUser(CreateUserCommand createUserCommand) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(createUserCommand.password());
        CreateUserCommand commandWithEncryptedPassword = new CreateUserCommand(
                createUserCommand.name(),
                createUserCommand.email(),
                createUserCommand.cellphone(),
                createUserCommand.cpf(),
                encryptedPassword
        );
        final User user = CreateUserMapper.toEntity(commandWithEncryptedPassword);

        return this.userRepository.save(user);
    }
}
