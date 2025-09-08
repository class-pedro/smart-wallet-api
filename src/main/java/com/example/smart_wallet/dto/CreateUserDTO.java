package com.example.smart_wallet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Cellphone cannot be empty")
    @Pattern(regexp = "^(\\(?\\d{2}\\)?9?\\d{8})$|^(\\(?\\d{2}\\)?\\d{8})$",
            message = "Invalid cellphone number")
    private String cellphone;

    @NotEmpty(message = "Document (CPF) cannot be empty")
    @CPF(message = "Invalid CPF")
    private String cpf;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).*$",
            message = "The password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 symbol.")
    private String password;


}
