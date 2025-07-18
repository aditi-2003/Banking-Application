package com.example.Banking.App.service.impl;

import com.example.Banking.App.dao.Account;
import com.example.Banking.App.dao.Transaction;
import com.example.Banking.App.dao.TransactionType;
import com.example.Banking.App.repository.AccountRepository;
import com.example.Banking.App.repository.TransactionRepository;
import com.example.Banking.App.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }
    @Override
    public Transaction createTransaction(Long accountId, TransactionType transactionType, double amount) {
        // Retrieve the Account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        log.info("error occurred for {} {}",accountId,TransactionServiceImpl.class);

        // Create the Transaction
        Transaction transaction = new Transaction();
        // We are getting TransactionHistory Cause: although it's @OneToMany mapping
        // in createTransaction method we are mapping the specific account to that transaction
        // So, The transactions are mapped to the Account entity also, and we are able to fetch
        // TransactionHistory from account entity object
        transaction.setAccount(account);
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        // Save the Transaction
        return transactionRepository.save(transaction);
    }
}
