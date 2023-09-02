package com.behappy.expenseapp.service.mapper;

import com.behappy.expenseapp.domain.Budget;
import com.behappy.expenseapp.domain.Expense;
import com.behappy.expenseapp.domain.ExpenseType;
import com.behappy.expenseapp.service.dto.ExpenseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ExpenseMapper {

    public static Expense toEntity(ExpenseDTO expenseDTO) {
        return Expense
                .builder()
                .id(expenseDTO.getId())
                .amount(expenseDTO.getAmount())
                .budget(Budget.builder().id(expenseDTO.getBudgetId()).build())
                .expenseType(ExpenseType.builder().id(expenseDTO.getExpenseTypeId()).build())
                .description(expenseDTO.getDescription())
                .status(expenseDTO.getStatus())
                .creationDate(expenseDTO.getDateCreation())
                .build();
    }

    public static ExpenseDTO toDTO(Expense expense) {
        return ExpenseDTO.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .budgetId(expense.getBudget().getId())
                .expenseTypeId(expense.getExpenseType().getId())
                .description(expense.getDescription())
                .status(expense.getStatus())
                .dateCreation(expense.getCreationDate())
                .build();
    }

    public static List<Expense> toEntities(List<ExpenseDTO> expenseDTOs) {
        if(expenseDTOs == null) return null;
        return expenseDTOs
                .stream()
                .map(ExpenseMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<ExpenseDTO> toDTOs(List<Expense> expenses) {
        if(expenses == null) return null;
        return expenses
                .stream()
                .map(ExpenseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
