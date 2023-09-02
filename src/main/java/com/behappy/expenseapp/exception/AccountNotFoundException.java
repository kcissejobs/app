package com.behappy.expenseapp.exception;

public class AccountNotFoundException extends RuntimeException {
    private final static long serialVersionUID = 1L;

    public AccountNotFoundException() {
        super("Account not found Exception");
    }
}