package com.example.smart_wallet.modules.expense.controller;

import com.example.smart_wallet.modules.expense.dto.CreateExpenseDTO;
import com.example.smart_wallet.modules.expense.dto.dashboardDTO.GetDashboardDTO;
import com.example.smart_wallet.modules.expense.service.DashboardService;
import com.example.smart_wallet.modules.expense.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/expenses")
@AllArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    private final DashboardService dashboardService;

    @PostMapping
    public void createExpense(@RequestBody CreateExpenseDTO createExpenseDTO) {
        expenseService.create(createExpenseDTO);
    }

    // TO DO: Remover esse método, está sendo criado somente para testar o retorno
    @GetMapping("dash")
    public GetDashboardDTO getDashboard(@RequestParam UUID walletId,
                                        @RequestParam(required = false) Integer month,
                                        @RequestParam(required = false) Integer year) {
        return dashboardService.getDashboard(walletId, year, month);
    }
}
