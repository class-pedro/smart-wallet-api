package com.example.smart_wallet.modules.expense.infrastructure.persistence.repository;

import com.example.smart_wallet.modules.expense.application.port.out.ExpenseRepository;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ExpenseJpaRepository extends JpaRepository<Expense, UUID>, ExpenseRepository {
    /**
     * Excludes the "root" placeholder row an installment purchase is saved under
     * (paymentMethod = installment, rootExpense null) — only its per-installment
     * children (rootExpense set) represent real charges, same filter the dashboard
     * statement query already relies on.
     */
    @Override
    @Query("""
            SELECT e FROM Expense e
            WHERE (e.wallet.id = :walletId OR e.card.wallet.id = :walletId)
              AND NOT (e.paymentMethod = 'installment' AND e.rootExpense IS NULL)
            ORDER BY e.purchaseDate DESC
            """)
    List<Expense> findTransactionsByWalletId(@Param("walletId") UUID walletId);
}
