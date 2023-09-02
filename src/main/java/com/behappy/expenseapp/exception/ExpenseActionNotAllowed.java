package com.behappy.expenseapp.exception;

public class ExpenseActionNotAllowed extends RuntimeException{
    public ExpenseActionNotAllowed() {
        super("Action not allowed");
    }
}
