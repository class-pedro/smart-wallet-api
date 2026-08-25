package com.example.smart_wallet.modules.expense.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "expense_nfc_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseNfcMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String accessKey;

    private LocalDateTime issuedAt;

    @OneToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @Column(columnDefinition = "TEXT")
    private String rawHtml;

    @OneToMany(
            mappedBy = "expenseNfcMetadata",
            cascade = CascadeType.ALL
    )
    private List<ExpenseNfcItem> expenseNfcItems;
}
