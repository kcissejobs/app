package com.behappy.expenseapp.repository;

import com.behappy.expenseapp.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select a from Account a where a.user.id = ?1")
    public List<Account> findUserAccounts(long userId);
}
