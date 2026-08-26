package com.example.smart_wallet.modules.user.infrastructure.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAuthRequest {

    @NotEmpty(message = "idToken cannot be empty")
    private String idToken;
}
