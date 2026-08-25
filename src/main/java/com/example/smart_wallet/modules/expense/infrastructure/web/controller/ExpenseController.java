package com.example.smart_wallet.modules.expense.infrastructure.web.controller;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.application.usecase.create.CreateExpenseUseCase;
import com.example.smart_wallet.modules.expense.application.usecase.importnfc.ImportExpenseFromNfcUseCase;
import com.example.smart_wallet.modules.expense.infrastructure.web.dto.CreateExpenseRequest;
import com.example.smart_wallet.modules.expense.infrastructure.web.dto.ImportExpenseFromNfcRequest;
import com.example.smart_wallet.modules.expense.infrastructure.web.mapper.CreateExpenseWebMapper;
import com.example.smart_wallet.modules.expense.infrastructure.web.mapper.ImportExpenseFromNfcWebMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
@AllArgsConstructor
public class ExpenseController {
    private final CreateExpenseUseCase createExpenseUseCase;
    private final ImportExpenseFromNfcUseCase importExpenseFromNfcUseCase;

    @PostMapping
    public void createExpense(@RequestBody CreateExpenseRequest createExpenseRequest) {
        createExpenseUseCase.execute(CreateExpenseWebMapper.toCommand(createExpenseRequest));
    }

    @PostMapping("/nfc")
    public ResponseEntity<CreateExpenseCommand> createExpenseFromNfc(
            @RequestBody @Valid ImportExpenseFromNfcRequest importExpenseFromNfcRequest) {
        CreateExpenseCommand newExpense = importExpenseFromNfcUseCase.execute(
                ImportExpenseFromNfcWebMapper.toCommand(importExpenseFromNfcRequest));

        return ResponseEntity.ok(newExpense);
    }
}
