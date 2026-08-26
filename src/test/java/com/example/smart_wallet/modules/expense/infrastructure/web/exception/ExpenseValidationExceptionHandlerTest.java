package com.example.smart_wallet.modules.expense.infrastructure.web.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseValidationExceptionHandlerTest {

    @Mock
    private MethodArgumentNotValidException exception;

    @Mock
    private BindingResult bindingResult;

    private final ExpenseValidationExceptionHandler handler = new ExpenseValidationExceptionHandler();

    @Test
    void extractsDefaultMessagesFromBindingErrors() {
        FieldError descriptionError = new FieldError("request", "description", "Name cannot be empty");
        FieldError costError = new FieldError("request", "cost", "Cost must be zero or positive");
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(descriptionError, costError));

        ResponseEntity<?> response = handler.handleValidation(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(List.of("Name cannot be empty", "Cost must be zero or positive"));
    }
}
