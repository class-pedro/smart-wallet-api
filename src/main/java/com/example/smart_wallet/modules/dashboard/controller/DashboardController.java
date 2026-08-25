package com.example.smart_wallet.modules.dashboard.controller;

import com.example.smart_wallet.modules.dashboard.dto.GetDashboardDTO;
import com.example.smart_wallet.modules.dashboard.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/expenses")
@AllArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("dash")
    public GetDashboardDTO getDashboard(@RequestParam UUID walletId,
                                        @RequestParam(required = false) Integer month,
                                        @RequestParam(required = false) Integer year) {
        return dashboardService.getDashboard(walletId, year, month);
    }
}
