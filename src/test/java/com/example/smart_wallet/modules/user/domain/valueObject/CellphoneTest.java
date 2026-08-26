package com.example.smart_wallet.modules.user.domain.valueObject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CellphoneTest {

    @Test
    void acceptsAValidCellphoneWithoutPlusSign() {
        Cellphone cellphone = new Cellphone("11987654321");

        assertThat(cellphone.value()).isEqualTo("11987654321");
    }

    @Test
    void acceptsAValidCellphoneWithPlusSign() {
        Cellphone cellphone = new Cellphone("+5511987654321");

        assertThat(cellphone.value()).isEqualTo("+5511987654321");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "123",
            "abc12345678",
            "1234567890123456"
    })
    void rejectsInvalidCellphone(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Cellphone(value));
    }
}
