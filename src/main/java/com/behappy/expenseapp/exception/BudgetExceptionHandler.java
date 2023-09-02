package com.behappy.expenseapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

@ControllerAdvice
public class BudgetExceptionHandler {

    @ExceptionHandler(BudgetNotFoundException.class)
    @ResponseBody
   public ApiException handleNotFoundException(BudgetNotFoundException budgetNotFoundException) {
       return ApiException.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(budgetNotFoundException.getMessage())
                .zonedDateTime(ZonedDateTime.now())
                .build();
   }

    @ExceptionHandler(BudgetStatusNotAllowed.class)
    @ResponseBody
    public ApiException handleStatusNotAllowedException(BudgetStatusNotAllowed budgetStatusNotAllowed) {
        return ApiException.builder()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                .errorMessage(budgetStatusNotAllowed.getMessage())
                .zonedDateTime(ZonedDateTime.now())
                .build();
    }
}
