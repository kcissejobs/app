package com.behappy.expenseapp.exception;

public class FinancialTransactionNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public  FinancialTransactionNotFoundException() {
        super("Financial Transaction not found");
    }
}
