package com.example.smart_wallet.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Values are persisted as an integer amount of cents; this converts them to reais for API responses. */
public class MoneyNormalizer {
    private MoneyNormalizer() {
        throw new IllegalStateException("Utility class");
    }

    public static BigDecimal centsToReais(BigDecimal cents) {
        if (cents == null) {
            return null;
        }
        return cents
                .divide(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.DOWN);
    }
}
