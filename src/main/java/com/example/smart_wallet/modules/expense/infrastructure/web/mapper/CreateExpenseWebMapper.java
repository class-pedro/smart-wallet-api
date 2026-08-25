package com.example.smart_wallet.modules.expense.infrastructure.web.mapper;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.infrastructure.web.dto.CreateExpenseRequest;

public class CreateExpenseWebMapper {
    public static CreateExpenseCommand toCommand(CreateExpenseRequest request) {
        return new CreateExpenseCommand(
                request.getDescription(),
                request.getCost(),
                request.getPaymentType(),
                request.getPaymentMethod(),
                request.getPaymentDate(),
                request.getPurchaseDate(),
                request.getInstallments(),
                request.getStatus(),
                request.getWalletId(),
                request.getCardId()
        );
    }
}
