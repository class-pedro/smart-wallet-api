package com.example.smart_wallet.modules.user.infrastructure.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteProfileRequest {

    @NotEmpty(message = "Document (CPF) cannot be empty")
    @CPF(message = "Invalid CPF")
    private String cpf;

    @NotEmpty(message = "Cellphone cannot be empty")
    @Pattern(regexp = "^(\\(?\\d{2}\\)?9?\\d{8})$|^(\\(?\\d{2}\\)?\\d{8})$",
            message = "Invalid cellphone number")
    private String cellphone;
}
