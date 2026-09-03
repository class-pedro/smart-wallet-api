package com.example.smart_wallet.modules.expense.application.port.out;

import com.example.smart_wallet.modules.expense.domain.entity.Expense;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository {
    Expense save(Expense expense);

    <S extends Expense> List<S> saveAll(Iterable<S> expenses);

    List<Expense> findTransactionsByWalletId(UUID walletId);
}
