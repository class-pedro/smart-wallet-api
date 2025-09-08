package com.example.smart_wallet.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseDTO {
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
    private String description;

    @NotNull(message = "Cost cannot be null")
    @PositiveOrZero(message = "Cost must be zero or positive")
    private BigDecimal cost;

    @NotBlank(message = "PaymentType cannot be empty")
    @Pattern(regexp = "credit|debit|money", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "PaymentType must be one of: credit, debit, money")
    private String paymentType;

    @NotBlank(message = "PaymentMethod cannot be empty")
    @Pattern(regexp = "payInFull|installment|recurrent", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "PaymentMethod must be one of: payInFull, installment, recurrent")
    private String paymentMethod;

    private LocalDateTime paymentDate;

    @NotNull(message = "PurchaseDate cannot be null")
    private LocalDateTime purchaseDate;

    private Integer installments;

    private String status;

    private String walletId;

    private String cardId;
}
