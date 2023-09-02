package com.behappy.expenseapp.service;

import com.behappy.expenseapp.domain.Budget;
import com.behappy.expenseapp.domain.Expense;
import com.behappy.expenseapp.domain.FinancialTransaction;
import com.behappy.expenseapp.exception.FinancialTransactionNotFoundException;
import com.behappy.expenseapp.repository.FinancialTransactionRepository;
import com.behappy.expenseapp.service.dto.FinancialTransactionDTO;
import com.behappy.expenseapp.service.mapper.FinancialTransactionMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@AllArgsConstructor
@Service
@Transactional
public class FinancialTransactionService {
    private final Logger log = LoggerFactory.getLogger(FinancialTransactionService.class);
    private final FinancialTransactionRepository ftRepository;
    //private final

    /**
     * Create a Financial Transaction
     * @param ftDTO
     * @return created FinancialTransactionDTO
     */
    public FinancialTransactionDTO createFinancialTransaction(FinancialTransactionDTO ftDTO) {
        log.debug("Create a new Financial Transaction {}", ftDTO);
        FinancialTransaction ft = FinancialTransactionMapper.toEntity(ftDTO);
        if(ft.isCredit() && ft.getAmount() < 0) {
            ft.setAmount(-ft.getAmount());
        }

        if(ft.isDebit() && ft.getAmount() > 0) {
            ft.setAmount(-ft.getAmount());
        }
        ft = ftRepository.save(ft);

       return FinancialTransactionMapper.toDTO(ft);
    }

    /**
     * Update a Financial Transaction
     * @param ftDTO financial transaction to update
     * @throws FinancialTransactionNotFoundException
     * if Financial transaction not found
     * @return the financial transaction updated
     */
    public FinancialTransactionDTO updateFinancialTransaction(FinancialTransactionDTO ftDTO) {
        log.debug("Update the Financial Transaction {}", ftDTO);
        FinancialTransaction ft = FinancialTransactionMapper.toEntity(ftDTO);
        if(!ftRepository.findById(ft.getId()).isPresent()) {
            throw new FinancialTransactionNotFoundException();
        }

        ft = ftRepository.save(ft);
        return FinancialTransactionMapper.toDTO(ft);
    }

    /**
     * Delete a financial transaction
     * @param id
     */
    public void deleteFinancialTransaction(Long id) {
        log.debug("delete the Financial Transaction {}", id);
         ftRepository.deleteById(id);
    }

    /**
     * Create FT of budget or Expense
     * @param parent
     * @param type
     * @param typeOperation
     * @return
     */
    public FinancialTransactionDTO createFinancialTransaction(
            Object parent,
            FinancialTransaction.Type type,
            FinancialTransaction.TypeOperation typeOperation
    ) {
         FinancialTransactionDTO ftDTO = null;

        if(parent instanceof Budget) {
            Budget budget = (Budget) parent;
            ftDTO = FinancialTransactionDTO.builder()
                    .accountId(budget.getAccount().getId())
                    .type(type)
                    .typeOperation(typeOperation)
                    .amount(budget.getAmount())
                    .parentId(budget.getId())
                    .localDateTime(LocalDate.now())
                    .build();
        } else if (parent instanceof Expense) {
            Expense expense = (Expense) parent;
            ftDTO = FinancialTransactionDTO.builder()
                    .accountId(expense.getBudget().getAccount().getId())
                    .type(type)
                    .typeOperation(typeOperation)
                    .amount(expense.getAmount())
                    .parentId(expense.getId())
                    .localDateTime(LocalDate.now())
                    .build();
        } else {
            throw new IllegalArgumentException();
        }

        return createFinancialTransaction(ftDTO);
    }
}
