package com.example.smart_wallet.modules.expense.infrastructure.web.controller;

import com.example.smart_wallet.modules.expense.application.usecase.create.CreateExpenseUseCase;
import com.example.smart_wallet.modules.expense.infrastructure.web.dto.CreateExpenseRequest;
import com.example.smart_wallet.modules.expense.infrastructure.web.mapper.CreateExpenseWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
@AllArgsConstructor
public class ExpenseController {
    private final CreateExpenseUseCase createExpenseUseCase;

    @PostMapping
    public void createExpense(@RequestBody CreateExpenseRequest createExpenseRequest) {
        createExpenseUseCase.execute(CreateExpenseWebMapper.toCommand(createExpenseRequest));
    }
}
