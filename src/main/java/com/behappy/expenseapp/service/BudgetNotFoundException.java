package com.behappy.expenseapp.service;

public class BudgetNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public BudgetNotFoundException() {
        super("Budget not found");
    }
}
