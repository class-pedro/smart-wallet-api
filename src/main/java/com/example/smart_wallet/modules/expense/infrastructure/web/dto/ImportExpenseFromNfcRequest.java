package com.example.smart_wallet.modules.expense.infrastructure.web.dto;

import com.example.smart_wallet.modules.expense.infrastructure.web.validation.annotation.ValidPaymentSource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidPaymentSource
public class ImportExpenseFromNfcRequest {
    @NotBlank(message = "url cannot be empty")
    private String url;

    @NotBlank(message = "PaymentType cannot be empty")
    @Pattern(regexp = "credit|debit|money", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "PaymentType must be one of: credit, debit, money")
    private String paymentType;

    private String walletId;

    private String cardId;
}
