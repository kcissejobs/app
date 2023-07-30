package com.behappy.expenseapp.service;

import com.behappy.expenseapp.domain.Budget;
import com.behappy.expenseapp.domain.Period;
import com.behappy.expenseapp.repository.BudgetRepository;
import com.behappy.expenseapp.service.dto.BudgetDTO;
import com.behappy.expenseapp.service.mapper.BudgetMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class BudgetService {
    private final Logger log = LoggerFactory.getLogger(BudgetService.class);
    private final BudgetRepository budgetRepository;

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
     * Create new Budget
     * @param budgetDTO
     * @return Budget created
     */
    public BudgetDTO createBudget(BudgetDTO budgetDTO) {
        log.debug("Request to create Budget {}", budgetDTO);
        Budget budget = BudgetMapper.toEntity(budgetDTO);
        budget = budgetRepository.save(budget);
        return BudgetMapper.toDTO(budget);
    }

    /**
     * Update Budget
     * @param budgetDTO
     * @return updated Budget
     */
    public BudgetDTO updateBudget(BudgetDTO budgetDTO) {
        log.debug("Request to update Budget {}", budgetDTO);
        Optional<Budget> optionalBudget = budgetRepository.findById(budgetDTO.getIdBudget());
        if(!optionalBudget.isPresent()) {
            throw  new BudgetNotFoundException();
        }

        Budget budget = optionalBudget.get();
        budget.setAmount(budgetDTO.getAmount());
        budget.setDescription(budgetDTO.getDescription());
        budget = budgetRepository.save(budget);
        return BudgetMapper.toDTO(budget);
    }

    /**
     * Delete a Budget
     * @param budgetId
     */
    public void deleteBudget(Long budgetId) {
        log.debug("Request to delete Budget with ID {}", budgetId);
        budgetRepository.findById(budgetId)
                .ifPresentOrElse(budget -> {budgetRepository.delete(budget);}, ()-> new BudgetNotFoundException());
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

}
