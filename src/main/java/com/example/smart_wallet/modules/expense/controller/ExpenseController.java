package com.example.smart_wallet.modules.expense.controller;

import com.example.smart_wallet.modules.expense.dto.CreateExpenseDTO;
import com.example.smart_wallet.modules.expense.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
@AllArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public void createExpense(@RequestBody CreateExpenseDTO createExpenseDTO) {
        expenseService.create(createExpenseDTO);
    }
}
