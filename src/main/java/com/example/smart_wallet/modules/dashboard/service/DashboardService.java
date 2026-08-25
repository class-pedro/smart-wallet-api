package com.example.smart_wallet.modules.dashboard.service;

import com.example.smart_wallet.modules.dashboard.dto.GetDashboardDTO;

import java.util.UUID;

public interface DashboardService {
    GetDashboardDTO getDashboard(UUID walletId, Integer year, Integer month);
}
