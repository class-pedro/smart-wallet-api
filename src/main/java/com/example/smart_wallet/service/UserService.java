package com.example.smart_wallet.service;

import com.example.smart_wallet.domain.entity.User;
import com.example.smart_wallet.dto.CreateUserDTO;

public interface UserService {
    void signUpUser(CreateUserDTO userDto);

    User createUser(CreateUserDTO userDto);
}
