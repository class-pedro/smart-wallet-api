package com.example.smart_wallet.modules.user.infrastructure.web.controller;

import com.example.smart_wallet.modules.user.application.dto.CompleteProfileCommand;
import com.example.smart_wallet.modules.user.application.port.out.GoogleIdentityProvider;
import com.example.smart_wallet.modules.user.application.usecase.completeProfile.CompleteProfileUseCase;
import com.example.smart_wallet.modules.user.application.usecase.googleAuth.AuthenticateWithGoogleUseCase;
import com.example.smart_wallet.modules.user.application.usecase.login.GenerateTokenUseCase;
import com.example.smart_wallet.modules.user.application.usecase.me.GetWalletIdUseCase;
import com.example.smart_wallet.modules.user.application.usecase.signup.SignUpUserUseCase;
import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.AuthenticationDTO;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.CompleteProfileRequest;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.CreateUserRequest;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.GoogleAuthRequest;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.LoginResponseDTO;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.MeDTO;
import com.example.smart_wallet.modules.user.infrastructure.web.mapper.CreateUserWebMapper;
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
    private final SignUpUserUseCase signUpUserUseCase;
    private final GenerateTokenUseCase generateTokenUseCase;
    private final GetWalletIdUseCase getWalletIdUseCase;
    private final GoogleIdentityProvider googleIdentityProvider;
    private final AuthenticateWithGoogleUseCase authenticateWithGoogleUseCase;
    private final CompleteProfileUseCase completeProfileUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getEmail(),
                authenticationDTO.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        String token = generateTokenUseCase.execute((User) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody @Valid CreateUserRequest createUserRequest) {
        signUpUserUseCase.execute(CreateUserWebMapper.toCommand(createUserRequest));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/me")
    public ResponseEntity<MeDTO> me(@RequestHeader("Authorization") String authHeader) {
        String walletId = getWalletIdUseCase.execute(authHeader);

        return ResponseEntity.ok(new MeDTO(walletId));
    }

    @PostMapping("/google")
    public ResponseEntity<LoginResponseDTO> google(@RequestBody @Valid GoogleAuthRequest googleAuthRequest) {
        var googleUserInfo = googleIdentityProvider.verify(googleAuthRequest.getIdToken());
        String token = authenticateWithGoogleUseCase.execute(googleUserInfo);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PatchMapping("/complete-profile")
    public ResponseEntity<LoginResponseDTO> completeProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CompleteProfileRequest completeProfileRequest) {
        String token = completeProfileUseCase.execute(authHeader,
                new CompleteProfileCommand(completeProfileRequest.getCpf(), completeProfileRequest.getCellphone()));

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
