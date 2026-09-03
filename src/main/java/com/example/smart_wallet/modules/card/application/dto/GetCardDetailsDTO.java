package com.example.smart_wallet.modules.card.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

/**
 * currentClosingDate/currentDueDate are java.sql.Date, not java.time.LocalDate: Hibernate's
 * native-query constructor projection casts the raw JDBC value straight to the constructor
 * parameter type with no temporal conversion, and the driver returns java.sql.Date for a
 * Postgres `date` column.
 */
@Getter
@AllArgsConstructor
public class GetCardDetailsDTO {
    private UUID id;
    private String name;
    private Integer creditLimit;
    private BigDecimal currentInvoice;
    private Date currentClosingDate;
    private Date currentDueDate;
}
