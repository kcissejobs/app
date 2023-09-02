package com.behappy.expenseapp.service.mapper;

import com.behappy.expenseapp.domain.Period;
import com.behappy.expenseapp.security.user.User;
import com.behappy.expenseapp.service.dto.PeriodDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class PeriodMapper {
    public static PeriodDTO toDTO(Period period) {
        return PeriodDTO.builder()
                .id(period.getId())
                .startDate(period.getStartDate())
                .endDate(period.getEndDate())
                .status(period.getStatus())
                .userId(period.getUser().getId())
                .build();
    }

    public static Period toEntity(PeriodDTO periodDTO) {
        return Period.builder()
                .id(periodDTO.getId())
                .startDate(periodDTO.getStartDate())
                .endDate(periodDTO.getEndDate())
                .status(periodDTO.getStatus())
                .user(User.builder().id(periodDTO.getUserId()).build())
                .build();
    }

    public static List<Period> toEntities(@NotNull List<PeriodDTO> periods) {
        return periods.stream().map(PeriodMapper::toEntity).collect(Collectors.toList());
    }

    public static List<PeriodDTO> toDTOs(@NotNull List<Period> periods) {
        return periods.stream().map(PeriodMapper::toDTO).collect(Collectors.toList());
    }
}
