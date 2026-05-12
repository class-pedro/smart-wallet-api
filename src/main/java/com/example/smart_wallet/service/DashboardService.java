package com.example.smart_wallet.service;

import com.example.smart_wallet.dto.dashboardDTO.GetDashboardDTO;

import java.util.UUID;

public interface DashboardService {
    GetDashboardDTO getDashboard(UUID walletId, Integer year, Integer month);
}
