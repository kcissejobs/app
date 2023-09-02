package com.behappy.expenseapp.repository;

import com.behappy.expenseapp.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query("SELECT b FROM Budget b where b.account.id = ?1")
    public List<Budget> findAccountBudgets(long accountId);

    @Query("SELECT b FROM Budget b where b.account.id = ?1 and b.status = ?2")
    public List<Budget> findAllFreezeBudgets(long accountId, Budget.Status status);

}
