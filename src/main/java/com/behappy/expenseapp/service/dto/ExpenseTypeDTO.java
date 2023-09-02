package com.behappy.expenseapp.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseTypeDTO {
    private Long id;
    private String description;
    private Integer userId;
}
