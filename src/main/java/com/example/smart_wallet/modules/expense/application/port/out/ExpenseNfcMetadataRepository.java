package com.example.smart_wallet.modules.expense.application.port.out;

import com.example.smart_wallet.modules.expense.domain.entity.ExpenseNfcMetadata;

public interface ExpenseNfcMetadataRepository {
    ExpenseNfcMetadata save(ExpenseNfcMetadata expenseNfcMetadata);
}
