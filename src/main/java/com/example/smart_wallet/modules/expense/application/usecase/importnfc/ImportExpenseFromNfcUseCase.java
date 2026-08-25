package com.example.smart_wallet.modules.expense.application.usecase.importnfc;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.application.dto.ImportExpenseFromNfcCommand;

public interface ImportExpenseFromNfcUseCase {
    CreateExpenseCommand execute(ImportExpenseFromNfcCommand importExpenseFromNfcCommand);
}
