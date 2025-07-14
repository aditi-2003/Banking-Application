package com.example.Banking.App.service;


import com.example.Banking.App.dao.Transaction;
import com.example.Banking.App.dao.TransactionType;

public interface TransactionService {
    Transaction createTransaction(Long accountId, TransactionType transactionType, double amount);

}
