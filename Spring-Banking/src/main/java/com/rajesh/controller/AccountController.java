package com.rajesh.controller;

import com.rajesh.entity.Account;
import com.rajesh.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService service;

    // ✅ Create Account
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return service.createAccount(account);
    }

    // ✅ Get All Accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return service.getAllAccounts();
    }

    // ✅ Get Account by ID
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return service.getAccount(id);
    }

    // ✅ Update Account
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id,
                                 @RequestBody Account account) {
        return service.updateAccount(id, account);
    }

    // ✅ Delete Account
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return "Account Deleted Successfully";
    }

    // ✅ Deposit
    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id,
                           @RequestParam double amount) {
        return service.deposit(id, amount);
    }

    // ✅ Withdraw
    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id,
                            @RequestParam double amount) {
        return service.withdraw(id, amount);
    }
}