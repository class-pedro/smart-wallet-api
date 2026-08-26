package com.example.smart_wallet.modules.user.application.usecase.completeProfile;

import com.example.smart_wallet.modules.user.application.dto.CompleteProfileCommand;
import com.example.smart_wallet.modules.user.application.port.out.TokenGenerator;
import com.example.smart_wallet.modules.user.application.port.out.UserRepository;
import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.wallet.application.usecase.create.CreateWalletUseCase;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompleteProfileUseCaseHandler implements CompleteProfileUseCase {
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final CreateWalletUseCase createWalletUseCase;

    @Override
    public String execute(String authHeader, CompleteProfileCommand command) {
        String email = tokenGenerator.getEmailFromToken(authHeader);
        User user = userRepository.findEntityByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        user.setCpf(command.cpf());
        user.setCellphone(command.cellphone());
        User savedUser = userRepository.save(user);

        if (savedUser.getWallet() == null) {
            Wallet wallet = createWalletUseCase.execute(savedUser);
            savedUser.setWallet(wallet);
        }

        return tokenGenerator.generateToken(savedUser);
    }
}
