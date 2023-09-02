package com.behappy.expenseapp.service.mapper;

import com.behappy.expenseapp.domain.Account;
import com.behappy.expenseapp.domain.FinancialTransaction;
import com.behappy.expenseapp.service.dto.FinancialTransactionDTO;

import java.util.List;
import java.util.stream.Collectors;

public class FinancialTransactionMapper {

    public static FinancialTransaction toEntity(FinancialTransactionDTO  ftDTO) {

       return FinancialTransaction.builder()
                .id(ftDTO.getId())
                .amount(ftDTO.getAmount())
                .localDateTime(ftDTO.getLocalDateTime())
                .type(ftDTO.getType())
                .typeOperation(ftDTO.getTypeOperation())
                .parentId(ftDTO.getParentId())
                .account(Account.builder().id(ftDTO.getAccountId()).build())
                .build();
    }

    public static FinancialTransactionDTO toDTO(FinancialTransaction ft) {

        return FinancialTransactionDTO.builder()
                .id(ft.getId())
                .amount(ft.getAmount())
                .localDateTime(ft.getLocalDateTime())
                .type(ft.getType())
                .typeOperation(ft.getTypeOperation())
                .accountId(ft.getAccount().getId())
                .build();
    }

    public static List<FinancialTransaction> toEntities(List<FinancialTransactionDTO> ftDTOs) {

       return ftDTOs.stream().map(FinancialTransactionMapper::toEntity).collect(Collectors.toList());
    }

    public static List<FinancialTransactionDTO> toDTOs(List<FinancialTransaction> fts) {

        return fts.stream().map(FinancialTransactionMapper::toDTO).collect(Collectors.toList());
    }
}
