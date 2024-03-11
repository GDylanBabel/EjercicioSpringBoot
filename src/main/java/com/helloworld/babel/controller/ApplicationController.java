package com.helloworld.babel.controller;

import com.helloworld.babel.model.Account;
import com.helloworld.babel.model.Transference;
import com.helloworld.babel.service.IAccountService;
import com.helloworld.babel.service.ITransferenceService;
import com.helloworld.babel.service.impl.AccountService;
import com.helloworld.babel.utils.LogUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;
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
    private static final Logger logger = LogUtils.getLogger(ApplicationController.class);

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
        if (amount <= 0) {
            logger.info(new ParameterizedMessage("Intento de transferencia con cantidad nula o negativa, cuentas: {} - {}", sender, reciever));
            return "No se pueden hacer transferencias negativas o sin cantidad";
        }
        if (accountSender == null || accountReciever == null) {
            logger.info("Intento de transferencia con cuentas inexistentes");
            return "Error, cuenta no existente";
        }
        this.transferenceService.makeTransference(accountSender, accountReciever, amount);
        return "Transferencia creada";
    }

    @RequestMapping(value = "/storeIncome", method = RequestMethod.POST)
    public String storeIncome(@RequestParam double amount, @RequestParam String userAccount) {
        if (amount <= 0) {
            logger.error(new ParameterizedMessage("Ingreso con cantidad invalidad intentado con cuenta: {}", userAccount));
            return "Ingreso invalido";
        }
        this.accountService.storeIncome(amount, getAccountByCode(userAccount));
        return "Ingreso realizado";
    }

    @RequestMapping(value = "/withdrawIncome", method = RequestMethod.POST)
    public double withdrawIncome(@RequestParam double amount, @RequestParam String userAccount) {
        if (amount <= 0) {
            logger.error(new ParameterizedMessage("Retirada de ingresos con cantidad invalidad intentado con cuenta: {}", userAccount,
                    new IllegalArgumentException()));
        }
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
