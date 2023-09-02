package com.behappy.expenseapp.exception;

public class ExpenseStatusNotAllowed extends RuntimeException{
    public ExpenseStatusNotAllowed() {
        super("Expense status not allowed");
    }
}
