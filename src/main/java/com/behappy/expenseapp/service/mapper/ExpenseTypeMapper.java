package com.behappy.expenseapp.service.mapper;

import com.behappy.expenseapp.domain.ExpenseType;
import com.behappy.expenseapp.security.user.User;
import com.behappy.expenseapp.service.dto.ExpenseTypeDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ExpenseTypeMapper {

    public static ExpenseType toEntity(ExpenseTypeDTO expenseTypeDTO) {
       return ExpenseType.builder()
                .id(expenseTypeDTO.getId())
                .user(User.builder().id(expenseTypeDTO.getUserId()).build())
                .description(expenseTypeDTO.getDescription())
                .build();
    }

    public static ExpenseTypeDTO toDTO(ExpenseType expenseType ) {
        return ExpenseTypeDTO.builder()
                .id(expenseType.getId())
                .userId(expenseType.getUser().getId())
                .description(expenseType.getDescription())
                .build();
    }

    public static List<ExpenseType> toEntities(List<ExpenseTypeDTO> expenseTypeDTOs) {
       return expenseTypeDTOs.stream()
                .map(ExpenseTypeMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<ExpenseTypeDTO> toDTOs(List<ExpenseType> expenseTypes) {
        return expenseTypes.stream()
                .map(ExpenseTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

}
