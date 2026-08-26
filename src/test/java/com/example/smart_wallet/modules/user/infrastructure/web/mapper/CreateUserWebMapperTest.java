package com.example.smart_wallet.modules.user.infrastructure.web.mapper;

import com.example.smart_wallet.modules.user.application.dto.CreateUserCommand;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.CreateUserRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserWebMapperTest {

    @Test
    void mapsRequestFieldsToCommand() {
        CreateUserRequest request = new CreateUserRequest(
                "Pedro",
                "pedro@example.com",
                "11987654321",
                "11144477735",
                "Password1!"
        );

        CreateUserCommand command = CreateUserWebMapper.toCommand(request);

        assertThat(command.name()).isEqualTo("Pedro");
        assertThat(command.email()).isEqualTo("pedro@example.com");
        assertThat(command.cellphone()).isEqualTo("11987654321");
        assertThat(command.cpf()).isEqualTo("11144477735");
        assertThat(command.password()).isEqualTo("Password1!");
    }
}
