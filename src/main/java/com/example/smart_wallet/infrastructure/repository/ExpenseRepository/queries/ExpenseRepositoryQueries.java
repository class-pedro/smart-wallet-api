package com.example.smart_wallet.infrastructure.repository.ExpenseRepository.queries;

public class ExpenseRepositoryQueries {
    private ExpenseRepositoryQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static final String GET_CREDIT_CARD_STATEMENTS = """
            WITH periods AS (
                SELECT
                    c.id AS card_id,
                    CASE\s
                        WHEN c.closing_date_day > c.due_date_day THEN\s
                            make_date(
                                COALESCE(:year, EXTRACT(YEAR FROM CURRENT_DATE)::int),
                                COALESCE(:month, EXTRACT(MONTH FROM CURRENT_DATE)::int),
                                LEAST(
                                    c.closing_date_day,
                                    EXTRACT(
                                        DAY FROM (
                                            date_trunc('month', make_date(
                                                COALESCE(:year, EXTRACT(YEAR FROM CURRENT_DATE)::int),
                                                COALESCE(:month, EXTRACT(MONTH FROM CURRENT_DATE)::int),
                                                1
                                            )) + interval '1 month - 1 day'
                                        )
                                    )::int
                                )
                            ) - INTERVAL '1 month'
                        ELSE
                            make_date(
                                COALESCE(:year, EXTRACT(YEAR FROM CURRENT_DATE)::int),
                                COALESCE(:month, EXTRACT(MONTH FROM CURRENT_DATE)::int),
                                LEAST(
                                    c.closing_date_day,
                                    EXTRACT(
                                        DAY FROM (
                                            date_trunc('month', make_date(
                                                COALESCE(:year, EXTRACT(YEAR FROM CURRENT_DATE)::int),
                                                COALESCE(:month, EXTRACT(MONTH FROM CURRENT_DATE)::int),
                                                1
                                            )) + interval '1 month - 1 day'
                                        )
                                    )::int
                                )
                            )
                    END AS current_closing,
                    CASE\s
                        WHEN c.closing_date_day > c.due_date_day THEN\s
                            make_date(
                                COALESCE(:year, EXTRACT(YEAR FROM CURRENT_DATE)::int),
                                COALESCE(:month, EXTRACT(MONTH FROM CURRENT_DATE)::int),
                                LEAST(
                                    c.closing_date_day,
                                    EXTRACT(
                                        DAY FROM (
                                            date_trunc('month', make_date(
                                                COALESCE(:year, EXTRACT(YEAR FROM CURRENT_DATE)::int),
                                                COALESCE(:month, EXTRACT(MONTH FROM CURRENT_DATE)::int),
                                                1
                                            )) + interval '1 month - 1 day'
                                        )
                                    )::int
                                )
                            ) - INTERVAL '2 month'
                        ELSE
                            make_date(
                                COALESCE(:year, EXTRACT(YEAR FROM CURRENT_DATE)::int),
                                COALESCE(:month, EXTRACT(MONTH FROM CURRENT_DATE)::int),
                                LEAST(
                                    c.closing_date_day,
                                    EXTRACT(
                                        DAY FROM (
                                            date_trunc('month', make_date(
                                                COALESCE(:year, EXTRACT(YEAR FROM CURRENT_DATE)::int),
                                                COALESCE(:month, EXTRACT(MONTH FROM CURRENT_DATE)::int),
                                                1
                                            )) + interval '1 month - 1 day'
                                        )
                                    )::int
                                )
                            ) - INTERVAL '1 month'
                    END AS previous_closing
                FROM cards c
                WHERE c.id IN :cardIds
            )
            SELECT
                e.card_id AS cardId,
                c.name AS name,
                SUM(e.cost) AS totalFatura
            FROM expenses e
            INNER JOIN cards c ON e.card_id = c.id
            INNER JOIN periods p ON p.card_id = c.id
            WHERE (
                    (e.payment_method IN ('installment') AND e.purchase_date BETWEEN p.previous_closing AND p.current_closing AND e.root_expense IS NOT NULL)
                    OR (e.payment_method = 'payInFull' AND e.purchase_date BETWEEN p.previous_closing AND p.current_closing)
                    OR e.payment_method = 'recurrent'
                )
              AND e.card_id IN :cardIds
            GROUP BY e.card_id, c.name
            """;

    public static final String GET_NON_CREDIT_RECURRENT_EXPENSES = """
            SELECT id,
                description,
                cost
            FROM expenses
            WHERE
                payment_type <> 'credit'
                AND payment_method = 'recurrent'
            """;

    public static final String GET_PAY_IN_FULL_EXPENSES_BY_MONTH_AND_YEAR = """
            SELECT id,
            description,
            cost
            FROM expenses
            WHERE\s
                DATE_TRUNC('month', payment_date) = DATE_TRUNC(
                    'month',
                    make_date(
                        COALESCE(:year, EXTRACT(YEAR FROM CURRENT_DATE)::int),
                        COALESCE(:month, EXTRACT(MONTH FROM CURRENT_DATE)::int),
                        1
                    )
                )
            AND payment_method = 'payInFull'
            AND payment_type IN ('debit', 'money')
            """;
}
