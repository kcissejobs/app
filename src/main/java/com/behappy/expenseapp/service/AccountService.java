package com.behappy.expenseapp.service;

import com.behappy.expenseapp.domain.Account;
import com.behappy.expenseapp.exception.AccountNotFoundException;
import com.behappy.expenseapp.repository.AccountRepository;
import com.behappy.expenseapp.security.user.User;
import com.behappy.expenseapp.service.dto.AccountDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    /**
     * Find Account by ID
     * @param accountId
     */
    public AccountDTO findAccountById(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isEmpty()) throw new AccountNotFoundException();

        Account account = optionalAccount.get();
        return AccountDTO.builder()
                .accountId(account.getId())
                .userId(account.getUser().getId())
                .build();
    }

}
