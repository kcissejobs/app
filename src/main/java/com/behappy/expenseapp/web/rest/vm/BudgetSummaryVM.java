package com.behappy.expenseapp.web.rest.vm;

import com.behappy.expenseapp.domain.Budget;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetSummaryVM {
    private String budgetDescription;
    private double budgetAmount;
    private Budget.Status status;
    private double totalExpenses;
}
