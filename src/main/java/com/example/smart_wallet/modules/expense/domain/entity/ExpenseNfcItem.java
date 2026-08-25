package com.example.smart_wallet.modules.expense.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "expense_nfc_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseNfcItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private BigDecimal quantity;

    private String unit;

    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "expense_nfc_metadata_id")
    private ExpenseNfcMetadata expenseNfcMetadata;
}
