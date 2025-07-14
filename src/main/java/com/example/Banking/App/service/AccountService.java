package com.example.Banking.App.service;

import com.example.Banking.App.dto.AccountDto;
import com.example.Banking.App.dto.TransactionDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
    AccountDto updateAccount(AccountDto accountDto);
    AccountDto deposit(Long id,double amount);
    AccountDto withdraw(Long id,double amount);
    List<AccountDto> getAllAccounts();
    void deleteAccount(Long id);
    List<TransactionDto> getTransactionHistory(Long accountId);
}