package com.behappy.expenseapp.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetDTO {
    private Long idBudget;
    private String description;
    private double amount;
    @NotNull
    private Long periodId;
    @NotNull
    private Long accountId;
}
