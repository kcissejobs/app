package com.behappy.expenseapp.repository;

import com.behappy.expenseapp.domain.FinancialTransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialTransactionDetailsRepository extends JpaRepository<FinancialTransactionDetails, Long> {
}
