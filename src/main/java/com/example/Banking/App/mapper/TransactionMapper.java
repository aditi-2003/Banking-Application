package com.example.Banking.App.mapper;

import com.example.Banking.App.dao.Account;
import com.example.Banking.App.dao.Transaction;
import com.example.Banking.App.dto.TransactionDto;

import java.util.ArrayList;
import java.util.List;

public class TransactionMapper {
    public static Transaction mapToTransaction(TransactionDto transactionDto, Account account) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setAccount(account);
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionDate(transactionDto.getTransactionDate());
        return transaction;
    }

    public static TransactionDto mapToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setAccountId(transaction.getAccount().getId());
        transactionDto.setTransactionType(transaction.getTransactionType());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setTransactionDate(transaction.getTransactionDate());
        return transactionDto;
    }

    public static List<TransactionDto> mapToTransactionDtoList(List<Transaction> transactions) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDtos.add(mapToTransactionDto(transaction));
        }
        return transactionDtos;
    }
}
