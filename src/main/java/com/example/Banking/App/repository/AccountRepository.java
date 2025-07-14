package com.example.Banking.App.repository;


import com.example.Banking.App.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {

}
