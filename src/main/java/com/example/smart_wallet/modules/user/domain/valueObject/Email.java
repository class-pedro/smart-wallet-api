package com.example.smart_wallet.modules.user.domain.valueObject;

public record Email(String value) {
    public Email {
        if (value == null || !value.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            throw new IllegalArgumentException("Email inválido");
        }
    }
}