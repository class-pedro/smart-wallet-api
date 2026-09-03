package com.example.smart_wallet.modules.expense.application.usecase.list;

import com.example.smart_wallet.modules.expense.application.dto.GetTransactionsDTO;

public interface ListTransactionsUseCase {
    GetTransactionsDTO execute(String walletId);
}
