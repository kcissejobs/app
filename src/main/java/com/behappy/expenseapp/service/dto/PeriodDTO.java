package com.behappy.expenseapp.service.dto;

import com.behappy.expenseapp.domain.Period;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PeriodDTO {
    private Long id;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    private Period.Status status;
    @NotNull
    private Integer userId;
}
