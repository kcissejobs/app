package com.behappy.expenseapp.repository;

import com.behappy.expenseapp.domain.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {
}