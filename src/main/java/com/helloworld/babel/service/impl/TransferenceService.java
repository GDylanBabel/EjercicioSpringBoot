package com.helloworld.babel.service.impl;

import com.helloworld.babel.model.Account;
import com.helloworld.babel.model.Transference;
import com.helloworld.babel.repository.ITransferenceRepository;
import com.helloworld.babel.service.ITransferenceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransferenceService implements ITransferenceService {

    private double BASE_INTEREST = 3.99;
    private ITransferenceRepository transferenceRepository;


    public TransferenceService(ITransferenceRepository transferenceRepository) {
        this.transferenceRepository = transferenceRepository;
    }

    @Override
    public void makeTransference(Account sender, Account reciever, double amount) {
        checkInterestAppliance(sender, reciever);
        registerTransference(sender, reciever, amount);
    }

    @Override
    public List<Transference> getOperations(Account account) {
        return this.transferenceRepository.getOperationsByAccount(account.getAccountNumber());
    }

    private void checkInterestAppliance(Account sender, Account reciever) {
        String bankSender = sender.getBankCode();
        String bankReciever = reciever.getBankCode();
        System.out.println(bankReciever);
        System.out.println(bankSender);
        if (bankSender.equalsIgnoreCase(bankReciever)) {
            return;
        }
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
        this.transferenceRepository.save(transference);
    }
}
