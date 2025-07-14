package com.example.Banking.App.mapper;

import com.example.Banking.App.dao.Account;
import com.example.Banking.App.dto.AccountDto;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto){
        Account account = new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance(),
                null
        );
        return account;
    }
    public static AccountDto mapToAccountDto( Account account){
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accountDto;

    }
}
