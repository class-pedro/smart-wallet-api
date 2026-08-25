package com.example.smart_wallet.modules.user.service;

import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.user.dto.CreateUserDTO;

public interface UserService {
    void signUpUser(CreateUserDTO userDto);

    User createUser(CreateUserDTO userDto);
}
