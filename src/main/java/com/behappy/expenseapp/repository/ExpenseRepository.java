package com.behappy.expenseapp.repository;

import com.behappy.expenseapp.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    public List<Expense> findExpensesByBudget_Id(long id);

    @Query("SELECT e FROM Expense e where e.budget.account.id= ?1 order by e.id desc")
    List<Expense> findAllAccountExpenses(Long accountId);

    @Query("SELECT e FROM Expense e where e.budget.account.id= ?1 and e.status = ?2  order by e.id desc")
    List<Expense> findAllFreezeExpenses(Long accountId, Expense.Status status);
}
