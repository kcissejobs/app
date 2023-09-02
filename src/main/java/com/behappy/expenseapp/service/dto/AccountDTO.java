package com.behappy.expenseapp.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountDTO {
    @NotNull
    private Long accountId;
    private Integer userId;
    private List<BudgetDTO> budgetDTOs;
}
