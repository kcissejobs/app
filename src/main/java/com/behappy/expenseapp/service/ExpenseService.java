package com.behappy.expenseapp.service;

import com.behappy.expenseapp.domain.Expense;
import com.behappy.expenseapp.domain.FinancialTransaction;
import com.behappy.expenseapp.exception.ExpenseActionNotAllowed;
import com.behappy.expenseapp.exception.ExpenseNotFoundException;
import com.behappy.expenseapp.exception.ExpenseStatusNotAllowed;
import com.behappy.expenseapp.repository.ExpenseRepository;
import com.behappy.expenseapp.service.dto.BudgetDTO;
import com.behappy.expenseapp.service.dto.ExpenseDTO;
import com.behappy.expenseapp.service.mapper.BudgetMapper;
import com.behappy.expenseapp.service.mapper.ExpenseMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ExpenseService {
    private final Logger log = LoggerFactory.getLogger(BudgetService.class);
    private final ExpenseRepository expenseRepository;
    private final FinancialTransactionService ftService;
    private final BudgetService budgetService;


    /**
     * Create a new expense
     * @param expenseDTO
     * @return created expense ExpenseDTO
     */
    public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
        log.debug("Request create a new expense {} ", expenseDTO);
        var expense = ExpenseMapper.toEntity(expenseDTO);

        expense = expenseRepository.save(expense);
        BudgetDTO budgetDTO = budgetService.findBudgetById(expenseDTO.getBudgetId());
        expense.setBudget(BudgetMapper.toEntity(budgetDTO));
        expense.setCreationDate(LocalDate.now());

        if(expense.getStatus().equals(Expense.Status.FREEZE)) {
            ftService.createFinancialTransaction(
                    expense,
                    FinancialTransaction.Type.EP,
                    FinancialTransaction.TypeOperation.DEBIT
            );
        }

        return ExpenseMapper.toDTO(expense);
    }

    /**
     * Update expense
     * @param expenseDTO
     * @return the updated expense
     */
    public ExpenseDTO updateExpense(ExpenseDTO expenseDTO) {
        log.debug("Request create a new expense {} ", expenseDTO);
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseDTO.getId());
        if(expenseOptional.isEmpty()) {
            throw new ExpenseNotFoundException();
        }
        var oldExpense = expenseOptional.get();
        if(isStatusNotAllowed(expenseDTO.getStatus(), oldExpense)) {
            throw new ExpenseStatusNotAllowed();
        }

        var expense = expenseRepository.save(ExpenseMapper.toEntity(expenseDTO));

        if(Expense.Status.CANCEL.equals(expenseDTO.getStatus())
                && !Expense.Status.CANCEL.equals(oldExpense.getStatus())) {
            ftService.createFinancialTransaction(
                    expense,
                    FinancialTransaction.Type.EX,
                    FinancialTransaction.TypeOperation.CREDIT
            );
        }

        return ExpenseMapper.toDTO(expense);
    }

    /**
     * Retrieve expense by ID
     * @param id
     * @return founded expense
     */
    public ExpenseDTO findExpenseById(Long id) {
        log.debug("Request to retrieve expense by ID {} ", id);
        var expenseOptional = expenseRepository.findById(id);
        if(expenseOptional.isEmpty()) throw new ExpenseStatusNotAllowed();

        return ExpenseMapper.toDTO(expenseOptional.get());
    }

    /**
     * Retrieve all account expenses
     * @return
     */
    public List<ExpenseDTO> findAllAccountExpenses(Long accountId) {
        log.debug("Request find all expenses");
        return ExpenseMapper.toDTOs(expenseRepository.findAllAccountExpenses(accountId));
    }

    /**
     * Retrieve all account freeze expenses
     * @return list of expenses
     */
    public List<ExpenseDTO> findAccountFreezeExpenses(Long accountId) {
        log.debug("Request find all expenses");
        return ExpenseMapper.toDTOs(expenseRepository.findAllFreezeExpenses(accountId, Expense.Status.FREEZE));
    }

    /**
     * Retrieve all expenses
     * @param budgetId
     * @return list of expenses
     */
    public List<ExpenseDTO> findAllBudgetExpenses(Long budgetId) {
        List<Expense> expenses = expenseRepository.findExpensesByBudget_Id(budgetId);
        return ExpenseMapper.toDTOs(expenses);
    }

    /**
     * Delete an expense
     * @param id
     */
    public void deleteExpense(Long id) {
        log.debug("Update the expense with ID {} ", id);
        Optional<Expense> expenseOptional = expenseRepository.findById(id);
        if(expenseOptional.isEmpty()) {
            throw new ExpenseNotFoundException();
        }

        Expense oldExpense = expenseOptional.get();
        if(!Expense.Status.PENDING.equals(oldExpense.getStatus())) {
            throw new ExpenseActionNotAllowed();
        }
        expenseRepository.deleteById(id);
    }

    /**
     * Check if the status is allowed
     * @param status
     * @param expense
     * @throws ExpenseStatusNotAllowed
     * @return true if allowed
     */
    private boolean isStatusNotAllowed(Expense.Status status, Expense expense) {
        if(expense.isPending()) {
            if(Expense.Status.CANCEL.equals(status)) return true;
        }

        if(expense.isFreeze()) {
            if(Expense.Status.PENDING.equals(status)) return true;
        }

        if(expense.isCanceled()) {
            if(!Expense.Status.CANCEL.equals(status)) return true;
        }

        return false;
    }

    public double getSumExpenseAmountOfBudget(Long accountId, List<BudgetDTO> budgetsOfOpenPeriods) {
        return expenseRepository.findAllAccountExpenses(accountId)
                .stream()
                .filter(expense -> expense.isFreeze())
                .filter(expense -> budgetsOfOpenPeriods
                        .stream()
                        .anyMatch(budgetDTO -> budgetDTO.getId() == expense.getBudget().getId())
                )
                .collect(Collectors.summingDouble(expenseDTO-> expenseDTO.getAmount()));
    }

    public Map<Long, List<ExpenseDTO>> getExpensesByTypeOfBudgets(List<BudgetDTO> budgetDTOS) {
      return  expenseRepository.findAll()
                .stream()
                .filter(expense -> expense.isFreeze())
                .filter(expense -> budgetDTOS
                        .stream()
                        .anyMatch(budgetDTO -> budgetDTO.getId() == expense.getBudget().getId())
                )
               .map(ExpenseMapper::toDTO)
               .collect(Collectors.groupingBy(ExpenseDTO::getExpenseTypeId));
    }
}
