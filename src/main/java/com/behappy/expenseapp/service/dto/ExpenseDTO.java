package com.behappy.expenseapp.service.dto;

import com.behappy.expenseapp.domain.Expense;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExpenseDTO {
    private Long id;
    private String description;
    private double amount;
    private Expense.Status status;
    private Long expenseTypeId;
    private LocalDate dateCreation;
    private Long budgetId;
}
