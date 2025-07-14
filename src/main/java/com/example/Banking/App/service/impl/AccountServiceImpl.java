package com.example.Banking.App.service.impl;

import com.example.Banking.App.dao.Account;

import com.example.Banking.App.dao.Transaction;
import com.example.Banking.App.dao.TransactionType;
import com.example.Banking.App.dto.AccountDto;
import com.example.Banking.App.dto.TransactionDto;
import com.example.Banking.App.exception.AccountNotFoundException;
import com.example.Banking.App.mapper.AccountMapper;
import com.example.Banking.App.mapper.TransactionMapper;
import com.example.Banking.App.repository.AccountRepository;
import com.example.Banking.App.service.AccountService;
import com.example.Banking.App.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {


    private AccountRepository accountRepository;
    private TransactionService transactionService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.transactionService=transactionService;
    }
    @Override
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        transactionService.createTransaction(savedAccount.getId(), TransactionType.DEPOSIT,savedAccount.getBalance());
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        try {
            Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account doesn't exist id :" + id));
            return AccountMapper.mapToAccountDto(account);
        } catch (AccountNotFoundException e) {
            log.info("Account not found: {} {}",id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.info("An unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while fetching account by id: " + id, e);
        }
    }

    @Override
    @Transactional
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account doesn't exist id :"+id));
        double currentAmount = account.getBalance();
        double newBalance = currentAmount + amount;
        account.setBalance(newBalance);
        Account savedAccount = accountRepository.save(account);
        transactionService.createTransaction(savedAccount.getId(),TransactionType.DEPOSIT,amount);
        return AccountMapper.mapToAccountDto(savedAccount);

    }

    @Override
    @Transactional
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account doesn't exist id :"+id));
        double currentBalance = account.getBalance();
        if (currentBalance >= amount) {
            double newBalance = currentBalance - amount;
            account.setBalance(newBalance);
            Account savedAccount = accountRepository.save(account);
            transactionService.createTransaction(savedAccount.getId(),TransactionType.WITHDRAWAL,amount);
            return AccountMapper.mapToAccountDto(account);
        } else {
            log.info("Your account doesn't have sufficient balance, your current balance is: ",account.getBalance());
            throw new RuntimeException("Your account doesn't have sufficient balance");
        }
    }
    @Override
    @Transactional
    public AccountDto updateAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map((account)-> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()->new AccountNotFoundException("Account doesn't exist id :"+id));
        accountRepository.deleteById(id);
    }


    @Override
    public List<TransactionDto> getTransactionHistory(Long id) {
        // Retrieve the account from the database
        Account account = accountRepository.findById(id)
                .orElseThrow(() ->new AccountNotFoundException("Account doesn't exist id :"+id));
        // Get the list of transactions associated with the account
        List<Transaction> transactions = account.getTransactions();
        // you can use transactions.stream().map((account)->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
        return TransactionMapper.mapToTransactionDtoList(transactions);
    }
}
