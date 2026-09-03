package com.example.smart_wallet.modules.card.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetCardDetailsDTO {
    private UUID id;
    private String name;
    private Integer creditLimit;
    private BigDecimal currentInvoice;
    private LocalDate currentClosingDate;
    private LocalDate currentDueDate;
}
