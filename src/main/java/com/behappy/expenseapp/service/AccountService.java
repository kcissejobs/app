package com.behappy.expenseapp.service;

import com.behappy.expenseapp.domain.Account;
import com.behappy.expenseapp.repository.AccountRepository;
import com.behappy.expenseapp.security.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    /**
     * Create an account
     * @param user
     * @return account
     */
    public Account createAccount(User user) {
        Account account = new Account();
        account.setUser(user);
        return accountRepository.save(account);
    }

    /**
     * Delete Account
     * @param accountId
     * @return true if success
     */
    public void deleteAccount(Long accountId) {
        accountRepository.findById(accountId)
                .ifPresentOrElse(account -> accountRepository.delete(account), ()-> new AccountNotFoundException());

    }



}
