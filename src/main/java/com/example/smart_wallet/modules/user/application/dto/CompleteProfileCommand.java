package com.example.smart_wallet.modules.user.application.dto;

public record CompleteProfileCommand(
        String cpf,
        String cellphone
) {
}
