package com.example.smart_wallet.infrastructure.repository.ExpenseRepository;

import com.example.smart_wallet.domain.entity.Expense;
import com.example.smart_wallet.dto.dashboardDTO.GetCreditCardStatementsDTO;
import com.example.smart_wallet.dto.dashboardDTO.GetNonCreditExpensesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import static com.example.smart_wallet.infrastructure.repository.ExpenseRepository.queries.ExpenseRepositoryQueries.*;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    @Query(value = GET_CREDIT_CARD_STATEMENTS, nativeQuery = true)
    List<GetCreditCardStatementsDTO> findStatementsByCards(Integer year, Integer month,List<UUID> cardIds);

    @Query(value = GET_NON_CREDIT_RECURRENT_EXPENSES, nativeQuery = true)
    List<GetNonCreditExpensesDTO> findNonCreditRecurrentExpenses();

    @Query(value = GET_PAY_IN_FULL_EXPENSES_BY_MONTH_AND_YEAR, nativeQuery = true)
    List<GetNonCreditExpensesDTO> findPayInFullExpensesByMonthAndYear(Integer year, Integer month);
}
