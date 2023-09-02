package com.behappy.expenseapp.service.dto;

import com.behappy.expenseapp.domain.FinancialTransaction;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FinancialTransactionDTO {
    private Long id;
    private double amount;
    private LocalDate localDateTime;
    private Long parentId;
    private FinancialTransaction.TypeOperation typeOperation;
    private FinancialTransaction.Type type;
    private Long accountId;

}
