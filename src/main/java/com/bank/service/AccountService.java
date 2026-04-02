package com.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entity.Account;
import com.bank.repository.AccountRepository;   // ✅ FIXED IMPORT

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    // CREATE
    public Account createAccount(Account account) {
        return repository.save(account);
    }

    // READ ALL
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    // READ BY ID
    public Account getAccountById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // UPDATE
    public Account updateAccount(Long id, Account updatedAccount) {

        Account existingAccount = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found for update"));

        // ✅ FIXED: using correct field name
        existingAccount.setName(updatedAccount.getName());
        existingAccount.setBalance(updatedAccount.getBalance());

        return repository.save(existingAccount);
    }

    // DELETE
    public void deleteAccount(Long id) {

        Account account = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found for delete"));

        repository.delete(account);
    }

    // DEPOSIT MONEY
    public Account deposit(Long id, Double amount) {

        Account account = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);

        return repository.save(account);
    }

    // WITHDRAW MONEY
    public Account withdraw(Long id, Double amount) {

        Account account = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        return repository.save(account);
    }
}