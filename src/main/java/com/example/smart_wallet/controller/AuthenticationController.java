package com.example.smart_wallet.controller;

import com.example.smart_wallet.domain.entity.User;
import com.example.smart_wallet.dto.AuthenticationDTO;
import com.example.smart_wallet.dto.CreateUserDTO;
import com.example.smart_wallet.dto.LoginResponseDTO;
import com.example.smart_wallet.dto.MeDTO;
import com.example.smart_wallet.service.TokenService;
import com.example.smart_wallet.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getEmail(),
                authenticationDTO.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody @Valid CreateUserDTO createUserDTO) {
        userService.signUpUser(createUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/me")
    public ResponseEntity<MeDTO> me(@RequestHeader("Authorization") String authHeader) {
        String walletId = tokenService.getWalletIdFromToken(authHeader);

        return ResponseEntity.ok(new MeDTO(walletId));
    }
}
