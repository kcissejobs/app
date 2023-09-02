package com.behappy.expenseapp.repository;

import com.behappy.expenseapp.domain.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
    @Query("SELECT e FROM ExpenseType e where e.user.id = ?1")
    public List<ExpenseType> findAllExpenses(long userId);
}
