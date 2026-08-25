package com.example.smart_wallet.modules.expense.application.usecase.create;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;

public interface CreateExpenseUseCase {
    Expense execute(CreateExpenseCommand createExpenseCommand);
}
