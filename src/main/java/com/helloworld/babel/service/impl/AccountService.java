package com.helloworld.babel.service.impl;

import com.helloworld.babel.model.Account;
import com.helloworld.babel.model.Transference;
import com.helloworld.babel.repository.IAccountRepository;
import com.helloworld.babel.service.IAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {

    private IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private static final Logger logger = LogManager.getLogger(AccountService.class);

    @Override
    public String createAccount(String userName) {
        Account account = new Account();
        account.setUserName(userName);
        account.setAccountNumber(UUID.randomUUID().toString());
        this.accountRepository.save(account);
        logger.info("Se ha creado una cuenta nueva");
        return account.getAccountNumber();
    }

    @Override
    public List<Account> getUserAccounts(String userName) {
        return this.accountRepository.findAllByUserName(userName);
    }


    @Override
    public List<Transference> getOperations(String userName) {
        return null;
    }

    @Override
    public Account getUserAccount(String accountCode) {
        return this.accountRepository.findByAccountCode(accountCode);
    }

    @Override
    public void storeIncome(double amount, Account userAccount) {
        userAccount.storeIncome(amount);
        this.accountRepository.save(userAccount);
    }

    @Override
    public double withdrawIncome(double amount, Account userAccount) {
        double finalAccountIncome = userAccount.withdrawIncome(amount);
        this.accountRepository.save(userAccount);
        return finalAccountIncome;
    }

    @Override
    public List<Account> getAll() {
        return this.accountRepository.findAll();
    }

}
