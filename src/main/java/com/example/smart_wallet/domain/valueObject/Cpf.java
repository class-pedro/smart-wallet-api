package com.example.smart_wallet.domain.valueObject;

import java.util.Objects;

public final class Cpf {

    private final String value;

    public Cpf(String value) {
        if (value == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo");
        }

        String digits = value.replaceAll("\\D", "");
        if (!digits.matches("\\d{11}") || !isValidCpf(digits)) {
            throw new IllegalArgumentException("CPF inválido: " + value);
        }

        this.value = digits;
    }

    public String getValue() {
        return value.toString();
    }

    private boolean isValidCpf(String cpf) {
        if (cpf.chars().distinct().count() == 1) return false;

        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum1 += digit * (10 - i);
            sum2 += digit * (11 - i);
        }

        int dv1 = (sum1 * 10) % 11;
        dv1 = (dv1 == 10) ? 0 : dv1;

        sum2 += dv1 * 2;
        int dv2 = (sum2 * 10) % 11;
        dv2 = (dv2 == 10) ? 0 : dv2;

        return dv1 == Character.getNumericValue(cpf.charAt(9)) &&
                dv2 == Character.getNumericValue(cpf.charAt(10));
    }

    @Override
    public String toString() {
        return "%s.%s.%s-%s".formatted(
                value.substring(0, 3),
                value.substring(3, 6),
                value.substring(6, 9),
                value.substring(9, 11)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cpf cpf)) return false;
        return Objects.equals(value, cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
