package com.example.smart_wallet.modules.expense.service;

import com.example.smart_wallet.modules.expense.dto.CreateExpenseDTO;

public interface ExpenseService {
    void create(CreateExpenseDTO createExpenseDTO);
}
