package com.behappy.expenseapp.web.rest.vm;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PeriodVM {
    private Long id;
    private Date startDate;
    private Date endDate;
    private double budgetAmount;
    private double expenseAmount;
}
