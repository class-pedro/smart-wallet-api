package com.example.smart_wallet.modules.user.domain.valueObject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void acceptsAValidEmail() {
        Email email = new Email("user.name@example.com");

        assertThat(email.value()).isEqualTo("user.name@example.com");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "invalid-email",
            "user@",
            "@example.com",
            "user@example",
            "user example@example.com"
    })
    void rejectsInvalidEmail(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Email(value));
    }
}
