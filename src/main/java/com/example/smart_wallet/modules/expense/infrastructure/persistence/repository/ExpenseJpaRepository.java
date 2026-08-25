package com.example.smart_wallet.modules.expense.infrastructure.persistence.repository;

import com.example.smart_wallet.modules.expense.application.port.out.ExpenseRepository;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseJpaRepository extends JpaRepository<Expense, UUID>, ExpenseRepository {
}
