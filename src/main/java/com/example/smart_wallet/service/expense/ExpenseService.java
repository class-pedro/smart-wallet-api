package com.example.smart_wallet.service.expense;

import com.example.smart_wallet.dto.CreateExpenseDTO;

public interface ExpenseService {
    void create(CreateExpenseDTO createExpenseDTO);
}
