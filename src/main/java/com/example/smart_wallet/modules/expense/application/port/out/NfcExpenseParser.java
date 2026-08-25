package com.example.smart_wallet.modules.expense.application.port.out;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.domain.entity.ExpenseNfcMetadata;

public interface NfcExpenseParser {
    CreateExpenseCommand parseExpense(String html);

    ExpenseNfcMetadata parseItems(String html);
}
