package com.behappy.expenseapp.exception;

public class BudgetStatusNotAllowed extends RuntimeException{
    public BudgetStatusNotAllowed() {
        super("Budget Status not allowed");
    }
}
