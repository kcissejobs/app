package com.behappy.expenseapp.repository;

import com.behappy.expenseapp.domain.FinancialTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialTransactionTypeRepository extends JpaRepository<FinancialTransactionType, Long> {
}
