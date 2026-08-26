package com.example.smart_wallet.modules.dashboard.application.usecase.dashboard;

import com.example.smart_wallet.modules.card.application.usecase.find.FindCardIdsByWalletUseCase;
import com.example.smart_wallet.modules.dashboard.application.dto.GetCreditCardStatementsDTO;
import com.example.smart_wallet.modules.dashboard.application.dto.GetDashboardDTO;
import com.example.smart_wallet.modules.dashboard.application.dto.GetDashboardExpenseDTO;
import com.example.smart_wallet.modules.dashboard.application.dto.GetNonCreditExpensesDTO;
import com.example.smart_wallet.modules.dashboard.application.port.out.DashboardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDashboardUseCaseHandlerTest {

    @Mock
    private DashboardRepository dashboardRepository;

    @Mock
    private FindCardIdsByWalletUseCase findCardIdsByWalletUseCase;

    @InjectMocks
    private GetDashboardUseCaseHandler handler;

    @Test
    void aggregatesStatementsAndExpensesIntoADashboard() {
        UUID walletId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        UUID recurrentId = UUID.randomUUID();
        UUID payInFullId = UUID.randomUUID();
        List<UUID> cardIds = List.of(cardId);

        when(findCardIdsByWalletUseCase.execute(walletId)).thenReturn(cardIds);
        when(dashboardRepository.findStatementsByCards(2026, 1, cardIds)).thenReturn(List.of(
                new GetCreditCardStatementsDTO(cardId, "Nubank", BigDecimal.valueOf(10000))
        ));
        when(dashboardRepository.findNonCreditRecurrentExpenses()).thenReturn(List.of(
                new GetNonCreditExpensesDTO(recurrentId, "Aluguel", BigDecimal.valueOf(150000))
        ));
        when(dashboardRepository.findPayInFullExpensesByMonthAndYear(2026, 1)).thenReturn(List.of(
                new GetNonCreditExpensesDTO(payInFullId, "Mercado", BigDecimal.valueOf(5000))
        ));

        GetDashboardDTO result = handler.execute(walletId, 2026, 1);

        assertThat(result.getTotal()).isEqualByComparingTo("1650.00");
        assertThat(result.getExpenses()).extracting(GetDashboardExpenseDTO::getDashboardExpenseId)
                .containsExactly(cardId, recurrentId, payInFullId);
        assertThat(result.getExpenses()).extracting(GetDashboardExpenseDTO::getDashboardExpenseCost)
                .containsExactly(
                        new BigDecimal("100.00"),
                        new BigDecimal("1500.00"),
                        new BigDecimal("50.00")
                );
    }

    @Test
    void returnsZeroTotalWhenThereAreNoExpenses() {
        UUID walletId = UUID.randomUUID();
        when(findCardIdsByWalletUseCase.execute(walletId)).thenReturn(List.of());
        when(dashboardRepository.findStatementsByCards(2026, 1, List.of())).thenReturn(List.of());
        when(dashboardRepository.findNonCreditRecurrentExpenses()).thenReturn(List.of());
        when(dashboardRepository.findPayInFullExpensesByMonthAndYear(2026, 1)).thenReturn(List.of());

        GetDashboardDTO result = handler.execute(walletId, 2026, 1);

        assertThat(result.getTotal()).isEqualByComparingTo("0.00");
        assertThat(result.getExpenses()).isEmpty();
    }
}
