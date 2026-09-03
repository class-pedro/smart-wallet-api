package com.example.smart_wallet.modules.card.infrastructure.persistence.repository.queries;

public class CardRepositoryQueries {
    private CardRepositoryQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static final String GET_CARD_IDS_BY_WALLET_ID = """
            SELECT id
            FROM cards
            WHERE wallet_id = :walletId
            """;

    public static final String GET_CARD_IDS_AND_NAMES = """
            SELECT id,
                name
            FROM cards
            WHERE wallet_id = :walletId
            """;

    public static final String GET_CARDS_WITH_CURRENT_INVOICE = """
            WITH periods AS (
                SELECT
                    c.id AS card_id,
                    CASE
                        WHEN c.closing_date_day > c.due_date_day THEN
                            make_date(
                                EXTRACT(YEAR FROM CURRENT_DATE)::int,
                                EXTRACT(MONTH FROM CURRENT_DATE)::int,
                                LEAST(c.closing_date_day, EXTRACT(DAY FROM (date_trunc('month', CURRENT_DATE) + INTERVAL '1 month - 1 day'))::int)
                            ) - INTERVAL '1 month'
                        ELSE
                            make_date(
                                EXTRACT(YEAR FROM CURRENT_DATE)::int,
                                EXTRACT(MONTH FROM CURRENT_DATE)::int,
                                LEAST(c.closing_date_day, EXTRACT(DAY FROM (date_trunc('month', CURRENT_DATE) + INTERVAL '1 month - 1 day'))::int)
                            )
                    END AS current_closing,
                    CASE
                        WHEN c.closing_date_day > c.due_date_day THEN
                            make_date(
                                EXTRACT(YEAR FROM CURRENT_DATE)::int,
                                EXTRACT(MONTH FROM CURRENT_DATE)::int,
                                LEAST(c.closing_date_day, EXTRACT(DAY FROM (date_trunc('month', CURRENT_DATE) + INTERVAL '1 month - 1 day'))::int)
                            ) - INTERVAL '2 month'
                        ELSE
                            make_date(
                                EXTRACT(YEAR FROM CURRENT_DATE)::int,
                                EXTRACT(MONTH FROM CURRENT_DATE)::int,
                                LEAST(c.closing_date_day, EXTRACT(DAY FROM (date_trunc('month', CURRENT_DATE) + INTERVAL '1 month - 1 day'))::int)
                            ) - INTERVAL '1 month'
                    END AS previous_closing,
                    make_date(
                        EXTRACT(YEAR FROM CURRENT_DATE)::int,
                        EXTRACT(MONTH FROM CURRENT_DATE)::int,
                        LEAST(c.due_date_day, EXTRACT(DAY FROM (date_trunc('month', CURRENT_DATE) + INTERVAL '1 month - 1 day'))::int)
                    ) AS current_due
                FROM cards c
                WHERE c.wallet_id = :walletId
            )
            -- current_closing is `timestamp`, not `date`, because one of its CASE branches
            -- subtracts an INTERVAL (date - interval yields timestamp in Postgres) — cast
            -- back to date explicitly so the native-query projection maps cleanly.
            SELECT
                c.id AS id,
                c.name AS name,
                c.credit_limit AS creditLimit,
                COALESCE(SUM(
                    CASE
                        WHEN (e.payment_method = 'installment' AND e.purchase_date BETWEEN p.previous_closing AND p.current_closing AND e.root_expense IS NOT NULL)
                          OR (e.payment_method = 'payInFull' AND e.purchase_date BETWEEN p.previous_closing AND p.current_closing)
                          OR e.payment_method = 'recurrent'
                        THEN e.cost
                        ELSE 0
                    END
                ), 0) AS currentInvoice,
                p.current_closing::date AS currentClosingDate,
                p.current_due::date AS currentDueDate
            FROM cards c
            INNER JOIN periods p ON p.card_id = c.id
            LEFT JOIN expenses e ON e.card_id = c.id
            WHERE c.wallet_id = :walletId
            GROUP BY c.id, c.name, c.credit_limit, p.current_closing, p.current_due
            """;
}
