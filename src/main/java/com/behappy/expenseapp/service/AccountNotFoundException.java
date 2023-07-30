package com.behappy.expenseapp.service;

public class AccountNotFoundException extends RuntimeException {
    private final static long serialVersionUID = 1L;

    public AccountNotFoundException() {
        super("Account not found Exception");
    }
}