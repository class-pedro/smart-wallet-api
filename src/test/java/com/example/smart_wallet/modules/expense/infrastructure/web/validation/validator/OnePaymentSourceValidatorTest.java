package com.example.smart_wallet.modules.expense.infrastructure.web.validation.validator;

import com.example.smart_wallet.modules.expense.infrastructure.web.dto.ImportExpenseFromNfcRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OnePaymentSourceValidatorTest {

    private final OnePaymentSourceValidator validator = new OnePaymentSourceValidator();

    @Test
    void isValidWhenOnlyWalletIdIsPresent() {
        ImportExpenseFromNfcRequest request = new ImportExpenseFromNfcRequest("url", "money", "wallet-1", null);

        assertThat(validator.isValid(request, null)).isTrue();
    }

    @Test
    void isValidWhenOnlyCardIdIsPresent() {
        ImportExpenseFromNfcRequest request = new ImportExpenseFromNfcRequest("url", "credit", null, "card-1");

        assertThat(validator.isValid(request, null)).isTrue();
    }

    @Test
    void isInvalidWhenBothAreNull() {
        ImportExpenseFromNfcRequest request = new ImportExpenseFromNfcRequest("url", "money", null, null);

        assertThat(validator.isValid(request, null)).isFalse();
    }

    @Test
    void isInvalidWhenBothArePresent() {
        ImportExpenseFromNfcRequest request = new ImportExpenseFromNfcRequest("url", "money", "wallet-1", "card-1");

        assertThat(validator.isValid(request, null)).isFalse();
    }
}
