package com.example.smart_wallet.modules.user.infrastructure.web.controller;

import com.example.smart_wallet.modules.user.application.usecase.login.GenerateTokenUseCase;
import com.example.smart_wallet.modules.user.application.usecase.me.GetWalletIdUseCase;
import com.example.smart_wallet.modules.user.application.usecase.signup.SignUpUserUseCase;
import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.AuthenticationDTO;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.CreateUserRequest;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.LoginResponseDTO;
import com.example.smart_wallet.modules.user.infrastructure.web.dto.MeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SignUpUserUseCase signUpUserUseCase;

    @Mock
    private GenerateTokenUseCase generateTokenUseCase;

    @Mock
    private GetWalletIdUseCase getWalletIdUseCase;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationController controller;

    @Test
    void loginAuthenticatesAndReturnsAToken() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("pedro@example.com", "Password1!");
        User principal = new User();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(generateTokenUseCase.execute(principal)).thenReturn("token-123");

        ResponseEntity<LoginResponseDTO> response = controller.login(authenticationDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAccess_token()).isEqualTo("token-123");
    }

    @Test
    void signUpMapsRequestAndReturns201() {
        CreateUserRequest request = new CreateUserRequest(
                "Pedro", "pedro@example.com", "11987654321", "11144477735", "Password1!");

        ResponseEntity<String> response = controller.signUp(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ArgumentCaptor<com.example.smart_wallet.modules.user.application.dto.CreateUserCommand> captor =
                ArgumentCaptor.forClass(com.example.smart_wallet.modules.user.application.dto.CreateUserCommand.class);
        verify(signUpUserUseCase).execute(captor.capture());
        assertThat(captor.getValue().email()).isEqualTo("pedro@example.com");
    }

    @Test
    void meReturnsTheWalletIdFromTheToken() {
        when(getWalletIdUseCase.execute("Bearer abc")).thenReturn("wallet-123");

        ResponseEntity<MeDTO> response = controller.me("Bearer abc");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().walletId()).isEqualTo("wallet-123");
    }
}
