package com.example.smart_wallet.modules.user.application.dto;

public record CreateUserCommand(
        String name,
        String email,
        String cellphone,
        String cpf,
        String password
) {
}
