package com.example.smart_wallet.modules.user.application.mapper;

import com.example.smart_wallet.modules.user.application.dto.CreateUserCommand;
import com.example.smart_wallet.modules.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CreateUserMapper {
    public static User toEntity(CreateUserCommand command) {
        User user = new User();
        user.setName(command.name());
        user.setEmail(command.email());
        user.setPasswordHash(command.password());
        user.setCpf(command.cpf());
        user.setCellphone(command.cellphone());

        return user;
    }
}
