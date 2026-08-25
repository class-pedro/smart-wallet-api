package com.example.smart_wallet.modules.expense.application.mapper;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;

public class CreateExpenseMapper {
    public static Expense toEntity(CreateExpenseCommand createExpenseCommand) {
        Expense expense = new Expense();
        expense.setDescription(createExpenseCommand.description());
        expense.setCost(createExpenseCommand.cost());
        expense.setPaymentType(createExpenseCommand.paymentType());
        expense.setPaymentMethod(createExpenseCommand.paymentMethod());
        expense.setPaymentDate(createExpenseCommand.paymentDate());
        expense.setPurchaseDate(createExpenseCommand.purchaseDate());
        expense.setInstallments(createExpenseCommand.installments());
        expense.setStatus(createExpenseCommand.status());

        return expense;
    }
}
