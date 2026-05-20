package com.bank.controller;

import com.bank.entity.Account;
import com.bank.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin(
    origins = {
        "https://abhiramb34.vercel.app",
        "http://localhost:5500"
    }
)

@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping
    public Account createAccount(
            @RequestBody Account account) {

        return accountRepository.save(account);
    }

    @GetMapping
    public List<Account> getAllAccounts() {

        return accountRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(
            @PathVariable Long id) {

        accountRepository.deleteById(id);

        return "Deleted";
    }
}