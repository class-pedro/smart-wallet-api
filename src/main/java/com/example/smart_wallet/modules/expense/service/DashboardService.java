package com.example.smart_wallet.modules.expense.service;

import com.example.smart_wallet.modules.expense.dto.dashboardDTO.GetDashboardDTO;

import java.util.UUID;

public interface DashboardService {
    GetDashboardDTO getDashboard(UUID walletId, Integer year, Integer month);
}
