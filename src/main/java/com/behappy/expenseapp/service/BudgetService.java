package com.behappy.expenseapp.service;

import com.behappy.expenseapp.domain.Budget;
import com.behappy.expenseapp.domain.FinancialTransaction;
import com.behappy.expenseapp.exception.BudgetNotFoundException;
import com.behappy.expenseapp.exception.BudgetStatusNotAllowed;
import com.behappy.expenseapp.repository.BudgetRepository;
import com.behappy.expenseapp.service.dto.BudgetDTO;
import com.behappy.expenseapp.service.dto.PeriodDTO;
import com.behappy.expenseapp.service.mapper.BudgetMapper;
import com.behappy.expenseapp.service.mapper.PeriodMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class BudgetService {
    private final Logger log = LoggerFactory.getLogger(BudgetService.class);
    private final BudgetRepository budgetRepository;
    private final FinancialTransactionService ftService;

    /**
     * Get all budget
     * @return list of budgets
     */
    public List<BudgetDTO> getAllBudgets() {
        log.debug("Request retrieve all budgets");
        List<Budget> budgets = budgetRepository.findAll();
        return BudgetMapper.toDTOs(budgets);
    }

    /**
     * Get all budget of an acount
     * @param accountId
     * @return list of budgets
     */
    public List<BudgetDTO> getAccountBudgets(@PathVariable long accountId) {
        log.debug("Request retrieve all budgets account {}", accountId);
        List<Budget> budgets = budgetRepository.findAccountBudgets(accountId);
        return BudgetMapper.toDTOs(budgets);
    }

    /**
     * Get all freeze budget of an acount
     * @param accountId
     * @return list of budgets
     */
    public List<BudgetDTO> getFreezeBudgets(@PathVariable long accountId) {
        log.debug("Request retrieve all freeze budgets account {}", accountId);
        List<Budget> budgets = budgetRepository.findAllFreezeBudgets(accountId, Budget.Status.FREEZE);
        return BudgetMapper.toDTOs(budgets);
    }

    /**
     * Create new Budget
     * @param budgetDTO
     * @return Budget created
     */
    public BudgetDTO createBudget(BudgetDTO budgetDTO) {
        log.debug("Request to create Budget {}", budgetDTO);
        Budget budget = BudgetMapper.toEntity(budgetDTO);
        budget = budgetRepository.save(budget);

        if(budget.isFreeze()) {
            ftService.createFinancialTransaction(
                    budget, FinancialTransaction.Type.BP,
                    FinancialTransaction.TypeOperation.CREDIT
            );
        }

        return BudgetMapper.toDTO(budget);
    }

    /**
     * Update Budget
     * @param budgetDTO
     * @return updated Budget
     */
    public BudgetDTO updateBudget(BudgetDTO budgetDTO) {
        log.debug("Request to update Budget {}", budgetDTO);
        Optional<Budget> optionalBudget = budgetRepository.findById(budgetDTO.getId());
        if(!optionalBudget.isPresent()) {
            throw  new BudgetNotFoundException();
        }

        Budget budget = optionalBudget.get();
        if(!budgetDTO.getStatus().equals(budget.getStatus())) {
            if(budget.isCanceled()) throw new BudgetStatusNotAllowed();

            if(budget.isFreeze() &&
                    budgetDTO.getStatus().equals(Budget.Status.PENDING)
            ) {
                throw new BudgetStatusNotAllowed();
            }

            if(budgetDTO.getStatus().equals(Budget.Status.FREEZE)) {
                ftService.createFinancialTransaction(
                        budget, FinancialTransaction.Type.BP,
                        FinancialTransaction.TypeOperation.CREDIT
                );
            }

            if(budgetDTO.getStatus().equals(Budget.Status.CANCEL) &&
                    budget.isFreeze()
            ) {
                ftService.createFinancialTransaction(
                        budget, FinancialTransaction.Type.BX,
                        FinancialTransaction.TypeOperation.DEBIT
                );
            }
        }

        budget.setAmount(budgetDTO.getAmount());
        budget.setDescription(budgetDTO.getDescription());
        budget.setStatus(budgetDTO.getStatus());
        budget.setPeriod(PeriodMapper.toEntity(budgetDTO.getPeriodDTO()));
        budget = budgetRepository.save(budget);
        return BudgetMapper.toDTO(budget);
    }


    /**
     * Delete a Budget
     * @param budgetId
     */
    public void deleteBudget(Long budgetId) {
        log.debug("Request to delete Budget with ID {}", budgetId);
       Optional<Budget> budgetOptional = budgetRepository.findById(budgetId);
       if(!budgetOptional.isPresent()) {
           throw new BudgetNotFoundException();
       }
       budgetRepository.deleteById(budgetId);
    }

    /**
     * Retrieve Budget
     * @param budgetId
     * @return the BudgetDTO found
     */
    public BudgetDTO findBudgetById(Long budgetId) {
        log.debug("Request to find Budget with ID {}", budgetId);
        Optional<Budget> optionalBudget = budgetRepository.findById(budgetId);
        if(optionalBudget.isEmpty()) return null;

        return BudgetMapper.toDTO(optionalBudget.get());
    }

    public List<BudgetDTO> findAllFreezeBudgets(Long accountId) {
        return this.getAccountBudgets(accountId)
                .stream()
                .filter(budgetDTO -> budgetDTO.getStatus().equals(Budget.Status.FREEZE))
                .collect(Collectors.toList());
    }

    public double getSumBudgetAmountOfPeriods(Long accountId, List<PeriodDTO> periodDTOs) {
        List<BudgetDTO> freezeBudgets = this.getFreezeBudgets(accountId)
                .stream()
                .filter(budgetDTO -> budgetDTO.getStatus().equals(Budget.Status.FREEZE))
                .collect(Collectors.toList());

        List<BudgetDTO> budgetsOfOpenPeriods = freezeBudgets
                .stream().filter(budgetDTO ->
                        periodDTOs.stream()
                                .anyMatch(
                                        openPeriod-> openPeriod.getId() == budgetDTO.getPeriodDTO().getId()
                                )
                )
                .collect(Collectors.toList());

        return budgetsOfOpenPeriods.stream()
                .collect(Collectors.summingDouble(budget-> budget.getAmount()));
    }

    public List<BudgetDTO> findBudgetsOfPeriods(List<PeriodDTO> periodDTOS) {
       return this.getAllBudgets()
                .stream().filter(budgetDTO ->
                        periodDTOS.stream()
                                .anyMatch(
                                        openPeriod-> openPeriod.getId() == budgetDTO.getPeriodDTO().getId()
                                )
                )
               .filter(budgetDTO -> budgetDTO.getStatus().equals(Budget.Status.FREEZE))
               .collect(Collectors.toList());
    }


    /**
     * Create a Financial Transaction of budget
     * @param budget
     * @return FinancialTransaction
     */
   /* private FinancialTransactionDTO createFinancialTransaction(
            Budget budget,
            FinancialTransaction.Type type,
            FinancialTransaction.TypeOperation typeOperation
    ) {
        FinancialTransactionDTO ftDTO = FinancialTransactionDTO
                .builder()
                .accountId(budget.getAccount().getId())
                .type(type)
                .typeOperation(typeOperation)
                .amount(budget.getAmount())
                .parentId(budget.getId())
                .build();

        return ftService.createFinancialTransaction(ftDTO);
    }*/
}
