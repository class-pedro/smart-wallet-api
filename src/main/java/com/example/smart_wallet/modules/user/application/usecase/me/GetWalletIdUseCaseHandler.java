package com.example.smart_wallet.modules.user.application.usecase.me;

import com.example.smart_wallet.modules.user.application.port.out.TokenGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetWalletIdUseCaseHandler implements GetWalletIdUseCase {
    private final TokenGenerator tokenGenerator;

    @Override
    public String execute(String authHeader) {
        return tokenGenerator.getWalletIdFromToken(authHeader);
    }
}
