package com.example.smart_wallet.controller;

import com.example.smart_wallet.dto.CreateUserDTO;
import com.example.smart_wallet.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserDTO userDto) {
        userService.signUpUser(userDto);

        return ResponseEntity.ok("Usuário criado");
    }
}
