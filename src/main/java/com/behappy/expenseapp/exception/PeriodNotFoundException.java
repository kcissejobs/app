package com.behappy.expenseapp.exception;

public class PeriodNotFoundException extends RuntimeException {
    public PeriodNotFoundException() {
        super("Period not found");
    }
}
