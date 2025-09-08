package com.example.smart_wallet.domain.valueObject;

public record Cellphone(String value) {
    public Cellphone {
        if (value == null || !value.matches("\\+?\\d{10,15}")) {
            throw new IllegalArgumentException("Número de celular inválido");
        }
    }
}
