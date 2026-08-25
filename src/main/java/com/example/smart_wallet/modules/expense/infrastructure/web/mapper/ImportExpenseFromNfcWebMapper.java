package com.example.smart_wallet.modules.expense.infrastructure.web.mapper;

import com.example.smart_wallet.modules.expense.application.dto.ImportExpenseFromNfcCommand;
import com.example.smart_wallet.modules.expense.infrastructure.web.dto.ImportExpenseFromNfcRequest;

public class ImportExpenseFromNfcWebMapper {
    public static ImportExpenseFromNfcCommand toCommand(ImportExpenseFromNfcRequest request) {
        return new ImportExpenseFromNfcCommand(
                request.getUrl(),
                request.getPaymentType(),
                request.getWalletId(),
                request.getCardId()
        );
    }
}
