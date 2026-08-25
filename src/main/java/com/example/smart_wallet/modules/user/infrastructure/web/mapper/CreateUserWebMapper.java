package com.example.smart_wallet.modules.user.infrastructure.web.mapper;

import com.example.smart_wallet.modules.user.application.dto.CreateUserCommand;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.CreateUserRequest;

public class CreateUserWebMapper {
    public static CreateUserCommand toCommand(CreateUserRequest request) {
        return new CreateUserCommand(
                request.getName(),
                request.getEmail(),
                request.getCellphone(),
                request.getCpf(),
                request.getPassword()
        );
    }
}
