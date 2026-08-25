package com.example.smart_wallet.modules.expense.infrastructure.web.validation.validator;

import com.example.smart_wallet.modules.expense.infrastructure.web.dto.ImportExpenseFromNfcRequest;
import com.example.smart_wallet.modules.expense.infrastructure.web.validation.annotation.ValidPaymentSource;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OnePaymentSourceValidator implements ConstraintValidator<ValidPaymentSource, ImportExpenseFromNfcRequest> {

    @Override
    public boolean isValid(ImportExpenseFromNfcRequest request, ConstraintValidatorContext context) {
        boolean hasWallet = request.getWalletId() != null;
        boolean hasCard = request.getCardId() != null;

        return hasWallet ^ hasCard;
    }
}
