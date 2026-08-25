package com.example.smart_wallet.modules.dashboard.infrastructure.persistence.repository;

import com.example.smart_wallet.modules.dashboard.application.dto.GetCreditCardStatementsDTO;
import com.example.smart_wallet.modules.dashboard.application.dto.GetNonCreditExpensesDTO;
import com.example.smart_wallet.modules.dashboard.application.port.out.DashboardRepository;
import com.example.smart_wallet.modules.expense.domain.entity.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

import static com.example.smart_wallet.modules.dashboard.infrastructure.persistence.repository.queries.DashboardRepositoryQueries.GET_CREDIT_CARD_STATEMENTS;
import static com.example.smart_wallet.modules.dashboard.infrastructure.persistence.repository.queries.DashboardRepositoryQueries.GET_NON_CREDIT_RECURRENT_EXPENSES;
import static com.example.smart_wallet.modules.dashboard.infrastructure.persistence.repository.queries.DashboardRepositoryQueries.GET_PAY_IN_FULL_EXPENSES_BY_MONTH_AND_YEAR;

public interface DashboardJpaRepository extends Repository<Expense, UUID>, DashboardRepository {
    @Override
    @Query(value = GET_CREDIT_CARD_STATEMENTS, nativeQuery = true)
    List<GetCreditCardStatementsDTO> findStatementsByCards(Integer year, Integer month, List<UUID> cardIds);

    @Override
    @Query(value = GET_NON_CREDIT_RECURRENT_EXPENSES, nativeQuery = true)
    List<GetNonCreditExpensesDTO> findNonCreditRecurrentExpenses();

    @Override
    @Query(value = GET_PAY_IN_FULL_EXPENSES_BY_MONTH_AND_YEAR, nativeQuery = true)
    List<GetNonCreditExpensesDTO> findPayInFullExpensesByMonthAndYear(Integer year, Integer month);
}
