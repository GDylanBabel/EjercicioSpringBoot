package com.helloworld.babel.service.impl;

import com.helloworld.babel.model.Account;
import com.helloworld.babel.model.Transference;
import com.helloworld.babel.repository.IAccountRepository;
import com.helloworld.babel.repository.ITransferenceRepository;
import com.helloworld.babel.service.ITransferenceService;
import com.helloworld.babel.utils.LogUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransferenceService implements ITransferenceService {

    private double BASE_INTEREST = 3.99;
    private ITransferenceRepository transferenceRepository;
    private IAccountRepository accountRepository;
    private static final Logger logger = LogUtils.getLogger(TransferenceService.class);

    public TransferenceService(ITransferenceRepository transferenceRepository, IAccountRepository accountRepository) {
        this.transferenceRepository = transferenceRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void makeTransference(Account sender, Account reciever, double amount) {
        if (amount <= 0) {
            logger.error("Transacción con 0 o menos € realizada");
            return;
        }
        checkInterestAppliance(sender, reciever);
        registerTransference(sender, reciever, amount);
    }

    @Override
    public List<Transference> getOperations(Account account) {
        if (account == null) {
            logger.error("Cuenta no existente");
            return new ArrayList<>();
        }
        return this.transferenceRepository.getOperationsByAccount(account.getAccountNumber());
    }

    private void checkInterestAppliance(Account sender, Account reciever) {
        String bankSender = sender.getBankCode();
        String bankReciever = reciever.getBankCode();
        System.out.println(bankReciever);
        System.out.println(bankSender);
        if (bankSender.equalsIgnoreCase(bankReciever)) {
            logger.info("Se ha realizado una transcción entre cuentas de un mismo banco");
            return;
        }
        logger.info("Se ha realizado una transcción entre cuentas de diferente banco");
        registerTransference(sender, reciever, BASE_INTEREST, "Interest apply");
    }

    private void registerTransference(Account sender, Account reciever, double amount) {
        registerTransference(sender, reciever, amount, "No concept");
    }

    private void registerTransference(Account sender, Account reciever, double amount, String concept) {
        Transference transference = new Transference();
        transference.setSenderAccount(sender.getAccountNumber());
        transference.setReceiverAccount(reciever.getAccountNumber());
        transference.setAmount(amount);
        transference.setConcept(concept);
        transference.setCode(UUID.randomUUID().toString());
        sender.setIncome(sender.getIncome() - amount);
        reciever.setIncome(reciever.getIncome() + amount);
        this.transferenceRepository.save(transference);
        this.accountRepository.save(sender);
        this.accountRepository.save(reciever);
    }
}
