package com.behappy.expenseapp.web.rest.vm;

import com.behappy.expenseapp.service.dto.PeriodDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class StatisticVM {
    private double budgetAmount;
    private double expenseAmount;
    private Map<String, Double> expenseAmountByType;
    private List<PeriodVM> periodVMList;

    private List<PeriodDTO> periodDTOs;
    private List<Double> amountBudgets;
    private List<Double> amountExpenses;

}
