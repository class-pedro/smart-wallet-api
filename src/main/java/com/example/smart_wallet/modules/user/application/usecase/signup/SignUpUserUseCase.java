package com.example.smart_wallet.modules.user.application.usecase.signup;

import com.example.smart_wallet.modules.user.application.dto.CreateUserCommand;

public interface SignUpUserUseCase {
    void execute(CreateUserCommand createUserCommand);
}
