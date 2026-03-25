package com.bank.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bank.entity.Account;
import com.bank.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    // CREATE ACCOUNT
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return service.createAccount(account);
    }

    // GET ALL ACCOUNTS
    @GetMapping
    public List<Account> getAllAccounts() {
        return service.getAllAccounts();
    }

    // GET ACCOUNT BY ID
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return service.getAccountById(id);
    }

    // UPDATE ACCOUNT
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id,
                                 @RequestBody Account updatedAccount) {
        return service.updateAccount(id, updatedAccount);
    }

    // DELETE ACCOUNT
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return "Account deleted successfully";
    }

    // DEPOSIT MONEY
    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id,
                           @RequestBody Map<String, Double> body) {

        Double amount = body.get("amount");
        return service.deposit(id, amount);
    }

    // WITHDRAW MONEY
    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id,
                            @RequestBody Map<String, Double> body) {

        Double amount = body.get("amount");
        return service.withdraw(id, amount);
    }
}