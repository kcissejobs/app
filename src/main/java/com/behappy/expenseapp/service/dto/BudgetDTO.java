package com.behappy.expenseapp.service.dto;

import com.behappy.expenseapp.domain.Budget;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetDTO {
    private Long id;
    private String description;
    private double amount;
    private Budget.Status status;
    @NotNull
    @JsonProperty("period")
    private PeriodDTO periodDTO;
    @NotNull
    private Long accountId;
}
