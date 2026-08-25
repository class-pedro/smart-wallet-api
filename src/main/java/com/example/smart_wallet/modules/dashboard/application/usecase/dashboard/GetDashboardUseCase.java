package com.example.smart_wallet.modules.dashboard.application.usecase.dashboard;

import com.example.smart_wallet.modules.dashboard.application.dto.GetDashboardDTO;

import java.util.UUID;

public interface GetDashboardUseCase {
    GetDashboardDTO execute(UUID walletId, Integer year, Integer month);
}
