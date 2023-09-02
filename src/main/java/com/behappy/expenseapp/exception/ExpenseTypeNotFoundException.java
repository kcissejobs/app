package com.behappy.expenseapp.exception;

public class ExpenseTypeNotFoundException extends RuntimeException{
    public ExpenseTypeNotFoundException() {
        super("Expense Type not found");
    }
}
