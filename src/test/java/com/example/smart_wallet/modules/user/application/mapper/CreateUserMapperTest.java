package com.example.smart_wallet.modules.user.application.mapper;

import com.example.smart_wallet.modules.user.application.dto.CreateUserCommand;
import com.example.smart_wallet.modules.user.domain.entity.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserMapperTest {

    @Test
    void mapsCommandFieldsToEntity() {
        CreateUserCommand command = new CreateUserCommand(
                "Pedro",
                "pedro@example.com",
                "11987654321",
                "11144477735",
                "encoded-password"
        );

        User user = CreateUserMapper.toEntity(command);

        assertThat(user.getName()).isEqualTo("Pedro");
        assertThat(user.getEmail()).isEqualTo("pedro@example.com");
        assertThat(user.getPasswordHash()).isEqualTo("encoded-password");
        assertThat(user.getCpf()).isEqualTo("11144477735");
        assertThat(user.getCellphone()).isEqualTo("11987654321");
    }
}
