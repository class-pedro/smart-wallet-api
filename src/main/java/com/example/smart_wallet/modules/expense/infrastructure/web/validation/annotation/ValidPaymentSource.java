package com.example.smart_wallet.modules.expense.infrastructure.web.validation.annotation;

import com.example.smart_wallet.modules.expense.infrastructure.web.validation.validator.OnePaymentSourceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OnePaymentSourceValidator.class)
public @interface ValidPaymentSource {

    String message() default "Exactly one of walletId or cardId must be informed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
