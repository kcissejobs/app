package com.behappy.expenseapp.service.mapper;

import com.behappy.expenseapp.domain.Account;
import com.behappy.expenseapp.domain.Budget;
import com.behappy.expenseapp.domain.Period;
import com.behappy.expenseapp.service.dto.BudgetDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BudgetMapper {
    public static Budget toEntity(BudgetDTO budgetDTO) {

        return Budget.builder()
                .id(budgetDTO.getIdBudget())
                .amount(budgetDTO.getAmount())
                .description(budgetDTO.getDescription())
                .period(Period.builder().id(budgetDTO.getPeriodId()).build())
                .account(Account.builder().id(budgetDTO.getAccountId()).build())
                .build();
    }

    public static BudgetDTO toDTO(Budget budget) {
        return BudgetDTO.builder()
                .idBudget(budget.getId())
                .amount(budget.getAmount())
                .description(budget.getDescription())
                .periodId(budget.getPeriod().getId())
                .accountId(budget.getAccount().getId())
                .build();
    }

    public static List<Budget> toEntities(List<BudgetDTO> budgetDTOS) {
       return budgetDTOS.stream()
               .map(BudgetMapper::toEntity)
               .collect(Collectors.toList());
    }

    public static List<BudgetDTO> toDTOs(List<Budget> budgets) {
        return budgets.stream()
                .map(BudgetMapper::toDTO)
                .collect(Collectors.toList());
    }
}
