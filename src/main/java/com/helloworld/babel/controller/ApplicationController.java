package com.helloworld.babel.controller;

import com.helloworld.babel.model.Account;
import com.helloworld.babel.model.Transference;
import com.helloworld.babel.service.IAccountService;
import com.helloworld.babel.service.ITransferenceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicationController {

    private IAccountService accountService;
    private ITransferenceService transferenceService;

    public ApplicationController(IAccountService accountService, ITransferenceService transferenceService) {
        this.accountService = accountService;
        this.transferenceService = transferenceService;
    }

    private Account getAccountByCode(String accountCode) {
        return this.accountService.getUserAccount(accountCode);
    }


    @RequestMapping(value = "/makeTransference", method = RequestMethod.POST)
    public String createUserTransference(@RequestParam String sender, @RequestParam String reciever, @RequestParam double amount) {
        Account accountSender = getAccountByCode(sender);
        Account accountReciever = getAccountByCode(reciever);
        this.transferenceService.makeTransference(accountSender, accountReciever, amount);
        return "Transferencia creada";
    }

    @RequestMapping(value = "/storeIncome", method = RequestMethod.POST)
    public String storeIncome(@RequestParam double amount, @RequestParam String userAccount) {
        this.accountService.storeIncome(amount, getAccountByCode(userAccount));
        return "Ingreso realizado";
    }

    @RequestMapping(value = "/withdrawIncome", method = RequestMethod.POST)
    public double withdrawIncome(@RequestParam double amount, @RequestParam String userAccount) {
        return this.accountService.withdrawIncome(amount, getAccountByCode(userAccount));
    }

    @RequestMapping(value = "/operations", method = RequestMethod.GET)
    public List<Transference> listOperations(@RequestParam String userAccount) {
        return this.transferenceService.getOperations(getAccountByCode(userAccount));
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public List<Account> listAccounts(@RequestParam String userName) {
        return this.accountService.getUserAccounts(userName);
    }

    @RequestMapping(value = "/create/account", method = RequestMethod.POST)
    public String createAccount(@RequestParam String userName) {
        return this.accountService.createAccount(userName);
    }

    @RequestMapping(value = "/get")
    public List<Account> getAllAccounts() {
        return this.accountService.getAll();
    }


}
