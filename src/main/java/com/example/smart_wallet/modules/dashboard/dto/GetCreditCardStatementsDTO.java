package com.example.smart_wallet.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetCreditCardStatementsDTO {
    private UUID cardId;
    private String cardName;
    private BigDecimal totalStatement;
}
