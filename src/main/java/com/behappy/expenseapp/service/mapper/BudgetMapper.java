package com.behappy.expenseapp.service.mapper;

import com.behappy.expenseapp.domain.Account;
import com.behappy.expenseapp.domain.Budget;
import com.behappy.expenseapp.service.dto.BudgetDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BudgetMapper {
    public static Budget toEntity(BudgetDTO budgetDTO) {

        return Budget.builder()
                .id(budgetDTO.getId())
                .amount(budgetDTO.getAmount())
                .description(budgetDTO.getDescription())
                .status(budgetDTO.getStatus())
                .period(PeriodMapper.toEntity(budgetDTO.getPeriodDTO()))
                .account(Account.builder().id(budgetDTO.getAccountId()).build())
                .build();
    }

    public static BudgetDTO toDTO(Budget budget) {
        return BudgetDTO.builder()
                .id(budget.getId())
                .amount(budget.getAmount())
                .status(budget.getStatus())
                .description(budget.getDescription())
                .periodDTO(PeriodMapper.toDTO(budget.getPeriod()))
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
