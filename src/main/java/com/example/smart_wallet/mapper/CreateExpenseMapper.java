package com.example.smart_wallet.mapper;

import com.example.smart_wallet.domain.entity.Expense;
import com.example.smart_wallet.dto.CreateExpenseDTO;

public class CreateExpenseMapper {
    public static Expense toEntity(CreateExpenseDTO createExpenseDTO) {
        Expense expense = new Expense();
        expense.setDescription(createExpenseDTO.getDescription());
        expense.setCost(createExpenseDTO.getCost());
        expense.setPaymentType(createExpenseDTO.getPaymentType());
        expense.setPaymentMethod(createExpenseDTO.getPaymentMethod());
        expense.setPaymentDate(createExpenseDTO.getPaymentDate());
        expense.setPurchaseDate(createExpenseDTO.getPurchaseDate());
        expense.setInstallments(createExpenseDTO.getInstallments());
        expense.setStatus(createExpenseDTO.getStatus());

        return expense;
    }
}
