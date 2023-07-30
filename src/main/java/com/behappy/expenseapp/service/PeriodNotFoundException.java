package com.behappy.expenseapp.service;

public class PeriodNotFoundException extends RuntimeException {
    public PeriodNotFoundException() {
        super("Period not found");
    }
}
