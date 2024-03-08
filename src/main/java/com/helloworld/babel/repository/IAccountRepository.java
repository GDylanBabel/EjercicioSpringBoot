package com.helloworld.babel.repository;

import com.helloworld.babel.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.accountNumber LIKE ?1")
    public Account findByAccountCode(String code);


    @Query("SELECT a FROM Account a WHERE a.userName = ?1")
    List<Account> findAllByUserName(String userName);

}
