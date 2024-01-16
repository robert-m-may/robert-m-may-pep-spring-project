package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
@Service
public class AccountService {
    @Autowired(required = true)
    private AccountRepository AccountDAO;

    public Account findAccountByUsername(String username) {
        Optional<Account> optionalAccount = AccountDAO.findByUsername(username);
        return optionalAccount.orElse(null);
    }

    public Account findAccountById(Integer account_id) {
        Optional<Account> optionalAccount = AccountDAO.findById(account_id);
        return optionalAccount.orElse(null);
    }

    public Account registerAccount(Account account) {
        Account checkAccount = findAccountByUsername(account.getUsername());
        return (checkAccount != null) ? null : AccountDAO.save(account);
    }

    public Account accountLogIn(Account account) {
        Account checkAccount = findAccountByUsername(account.getUsername());
        return (checkAccount != null
                && checkAccount.getPassword().equals(account.getPassword())) ? checkAccount : null;
    }
}
