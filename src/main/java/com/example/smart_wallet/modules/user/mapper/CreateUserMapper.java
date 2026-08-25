package com.example.smart_wallet.modules.user.mapper;

import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.user.dto.CreateUserDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateUserMapper {
    public static User toEntity(CreateUserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPassword());
        user.setCpf(dto.getCpf());
        user.setCellphone(dto.getCellphone());

        return user;
    }
}
