package com.example.smart_wallet.modules.user.application.usecase.login;

import com.example.smart_wallet.modules.user.application.port.out.TokenGenerator;
import com.example.smart_wallet.modules.user.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GenerateTokenUseCaseHandler implements GenerateTokenUseCase {
    private final TokenGenerator tokenGenerator;

    @Override
    public String execute(User user) {
        return tokenGenerator.generateToken(user);
    }
}
