package com.rajesh.service;

import com.rajesh.entity.Account;
import com.rajesh.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

    public Account createAccount(Account acc) {
        return repo.save(acc);
    }

    public List<Account> getAllAccounts() {
        return repo.findAll();
    }

    public Account getAccount(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Account updateAccount(Long id, Account acc) {
        Account existing = repo.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(acc.getName());
            existing.setBalance(acc.getBalance());
            return repo.save(existing);
        }
        return null;
    }

    public void deleteAccount(Long id) {
        repo.deleteById(id);
    }

    public Account deposit(Long id, double amount) {
        Account acc = repo.findById(id).orElse(null);
        if (acc != null) {
            acc.setBalance(acc.getBalance() + amount);
            return repo.save(acc);
        }
        return null;
    }

    public Account withdraw(Long id, double amount) {
        Account acc = repo.findById(id).orElse(null);
        if (acc != null && acc.getBalance() >= amount) {
            acc.setBalance(acc.getBalance() - amount);
            return repo.save(acc);
        }
        return null;
    }
}