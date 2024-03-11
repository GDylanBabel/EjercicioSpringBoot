package com.helloworld.babel.service.impl;

import com.helloworld.babel.model.Account;
import com.helloworld.babel.model.Transference;
import com.helloworld.babel.repository.IAccountRepository;
import com.helloworld.babel.service.IAccountService;
import com.helloworld.babel.utils.LogUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {

    private IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private static final Logger logger = LogUtils.getLogger(AccountService.class);

    @Override
    public String createAccount(String userName) {
        Account account = new Account();
        account.setUserName(userName);
        account.setAccountNumber("ES" + randomIBAN());
        this.accountRepository.save(account);
        logger.info(new ParameterizedMessage("Se ha creado una cuenta nueva: {} - {}", account.getUserName(), account.getAccountNumber()));
        return account.getAccountNumber();
    }

    // Method to generate a random number with specified number of digits
    private long randomIBAN() {
        Random random = new Random();
        // Calculate the minimum and maximum values for the specified number of digits
        // Generate the first 16 digits
        long firstPart = (long) (random.nextDouble() * 9_000_000_000_000_000L) + 1_000_000_000_000_000L;
        // Generate the remaining 8 digits
        long secondPart = (long) (random.nextDouble() * 1_000_000_000L);
        // Combine both parts to form the final 24-digit number
        return Math.abs(firstPart * 1_000_000_000L + secondPart);
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
