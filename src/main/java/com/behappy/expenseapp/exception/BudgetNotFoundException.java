package com.behappy.expenseapp.exception;

public class BudgetNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public BudgetNotFoundException() {
        super("Budget not found");
    }
}
