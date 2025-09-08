package com.example.smart_wallet.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardDTO {
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
    private String name;

    private Integer balance;

    private Integer dueDateDay;

    private Integer closingDateDay;

    private Integer creditLimit;

    @NotNull(message = "cardTypeId cannot be null")
    private UUID cardTypeId;

    @NotNull(message = "walletId cannot be null")
    private UUID walletId;
}
