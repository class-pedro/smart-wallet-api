WITH period AS (
    SELECT
        make_date(EXTRACT(YEAR FROM CURRENT_DATE)::int, EXTRACT(MONTH FROM CURRENT_DATE)::int, closing_date_day) AS current_closing,
        make_date(EXTRACT(YEAR FROM CURRENT_DATE)::int, EXTRACT(MONTH FROM CURRENT_DATE)::int, closing_date_day) - INTERVAL '1 month' AS previous_closing
FROM cards
WHERE id = '0ce9abcc-940d-4966-a1de-e70b01acd70c' -- USAR PARAMETRO
    )
SELECT
    -- expenses.description,
    -- expenses.cost,
    -- expenses.purchase_date,
    -- expenses.payment_method,
    -- expenses.root_expense,
    -- cards.name,
    -- cards.closing_date_day
    SUM(expenses.cost)
FROM
    expenses
        INNER JOIN cards
                   ON expenses.card_id = cards.id
        JOIN period ON true
WHERE
    (
        (expenses.payment_method IN ('installment') AND expenses.purchase_date BETWEEN previous_closing AND current_closing AND expenses.root_expense IS NOT NULL)
            OR (expenses.payment_method = 'payInFull' AND expenses.purchase_date BETWEEN previous_closing AND current_closing)
            OR expenses.payment_method = 'recurrent'
        )
  AND expenses.card_id = '0ce9abcc-940d-4966-a1de-e70b01acd70c' -- USAR PARAMETRO