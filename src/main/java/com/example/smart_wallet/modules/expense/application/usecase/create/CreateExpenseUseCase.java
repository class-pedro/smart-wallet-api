package com.example.smart_wallet.modules.expense.application.usecase.create;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;

public interface CreateExpenseUseCase {
    void execute(CreateExpenseCommand createExpenseCommand);
}
