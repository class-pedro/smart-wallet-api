package com.example.smart_wallet.modules.user.domain.valueObject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CpfTest {

    @Test
    void acceptsAValidCpf() {
        Cpf cpf = new Cpf("111.444.777-35");

        assertThat(cpf.getValue()).isEqualTo("11144477735");
    }

    @Test
    void acceptsAValidCpfWithoutFormatting() {
        Cpf cpf = new Cpf("52998224725");

        assertThat(cpf.getValue()).isEqualTo("52998224725");
    }

    @Test
    void formatsToStringWithMask() {
        Cpf cpf = new Cpf("11144477735");

        assertThat(cpf.toString()).isEqualTo("111.444.777-35");
    }

    @Test
    void equalsAndHashCodeAreBasedOnDigits() {
        Cpf a = new Cpf("111.444.777-35");
        Cpf b = new Cpf("11144477735");

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
        assertThat(a).isNotEqualTo("11144477735");
        assertThat(a).isEqualTo(a);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "111.111.111-11",
            "123",
            "111.444.777-99",
            "abcdefghijk"
    })
    void rejectsInvalidCpf(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Cpf(value));
    }
}
