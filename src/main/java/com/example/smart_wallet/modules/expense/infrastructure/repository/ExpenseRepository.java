package com.example.smart_wallet.modules.expense.infrastructure.repository;

import com.example.smart_wallet.modules.expense.domain.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
}
