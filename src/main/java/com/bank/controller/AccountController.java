package com.bank.controller;

import com.bank.entity.Account;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    // CREATE ACCOUNT
    @PostMapping("/create")
    public Account create(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    // GET ALL ACCOUNTS
    @GetMapping
    public List<Account> getAll() {
        return accountService.getAllAccounts();
    }

    // DEPOSIT
    @PutMapping("/deposit/{id}")
    public Account deposit(@PathVariable("id") Long id,
                           @RequestParam double amount) {
        return accountService.deposit(id, amount);
    }

    // WITHDRAW
    @PutMapping("/withdraw/{id}")
    public Account withdraw(@PathVariable("id") Long id,
                            @RequestParam double amount) {
        return accountService.withdraw(id, amount);
    }

    // DELETE ACCOUNT
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
        return "Deleted Successfully";
    }
}