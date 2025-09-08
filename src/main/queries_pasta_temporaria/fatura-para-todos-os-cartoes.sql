WITH periods AS (
    SELECT
        c.id AS card_id,
        make_date(EXTRACT(YEAR FROM CURRENT_DATE)::int, EXTRACT(MONTH FROM CURRENT_DATE)::int, c.closing_date_day) AS current_closing,
        make_date(EXTRACT(YEAR FROM CURRENT_DATE)::int, EXTRACT(MONTH FROM CURRENT_DATE)::int, c.closing_date_day) - INTERVAL '1 month' AS previous_closing
FROM cards c
WHERE c.id IN (:cardIds)  -- lista de IDs
    )
SELECT
    e.card_id,
    SUM(e.cost) AS total_fatura
FROM expenses e
         INNER JOIN cards c ON e.card_id = c.id
         INNER JOIN periods p ON p.card_id = c.id
WHERE (
    (e.payment_method IN ('installment') AND e.purchase_date BETWEEN p.previous_closing AND p.current_closing AND e.root_expense IS NOT NULL)
        OR (e.payment_method = 'payInFull' AND e.purchase_date BETWEEN p.previous_closing AND p.current_closing)
        OR e.payment_method = 'recurrent'
    )
  AND e.card_id IN (:cardIds)
GROUP BY e.card_id;
