package com.example.smart_wallet.modules.user.application.usecase.googleAuth;

import com.example.smart_wallet.modules.user.application.dto.GoogleUserInfo;
import com.example.smart_wallet.modules.user.application.port.out.TokenGenerator;
import com.example.smart_wallet.modules.user.application.port.out.UserRepository;
import com.example.smart_wallet.modules.user.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticateWithGoogleUseCaseHandler implements AuthenticateWithGoogleUseCase {
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;

    @Override
    public String execute(GoogleUserInfo googleUserInfo) {
        User user = userRepository.findEntityByEmail(googleUserInfo.email())
                .map(existingUser -> linkGoogleAccount(existingUser, googleUserInfo))
                .orElseGet(() -> createUser(googleUserInfo));

        return tokenGenerator.generateToken(user);
    }

    private User linkGoogleAccount(User user, GoogleUserInfo googleUserInfo) {
        if (user.getGoogleId() == null) {
            user.setGoogleId(googleUserInfo.googleId());
            return userRepository.save(user);
        }

        return user;
    }

    private User createUser(GoogleUserInfo googleUserInfo) {
        User user = new User();
        user.setName(googleUserInfo.name());
        user.setEmail(googleUserInfo.email());
        user.setGoogleId(googleUserInfo.googleId());

        return userRepository.save(user);
    }
}
