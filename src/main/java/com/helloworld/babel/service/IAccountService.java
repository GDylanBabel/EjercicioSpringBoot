package com.helloworld.babel.service;

import com.helloworld.babel.model.Account;
import com.helloworld.babel.model.Transference;

import java.util.List;

public interface IAccountService {

    public String createAccount(String userName);

    public List<Account> getUserAccounts(String userName);

    public List<Transference> getOperations(String userName);

    public Account getUserAccount(String accountCode);

    public void storeIncome(double amount, Account account);

    public double withdrawIncome(double amount, Account account);

    List<Account> getAll();
}
