package com.example.smart_wallet.modules.dashboard.infrastructure.web.controller;

import com.example.smart_wallet.modules.dashboard.application.dto.GetDashboardDTO;
import com.example.smart_wallet.modules.dashboard.application.usecase.dashboard.GetDashboardUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {

    @Mock
    private GetDashboardUseCase getDashboardUseCase;

    @InjectMocks
    private DashboardController controller;

    @Test
    void delegatesToUseCaseWithYearAndMonthInTheRightOrder() {
        UUID walletId = UUID.randomUUID();
        GetDashboardDTO expected = new GetDashboardDTO(BigDecimal.TEN, List.of());
        when(getDashboardUseCase.execute(walletId, 2026, 3)).thenReturn(expected);

        GetDashboardDTO result = controller.getDashboard(walletId, 3, 2026);

        assertThat(result).isEqualTo(expected);
        verify(getDashboardUseCase).execute(walletId, 2026, 3);
    }
}
