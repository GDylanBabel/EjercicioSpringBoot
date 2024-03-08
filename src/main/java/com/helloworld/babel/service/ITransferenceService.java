package com.helloworld.babel.service;

import com.helloworld.babel.model.Account;
import com.helloworld.babel.model.Transference;

import java.util.List;

public interface ITransferenceService {

    public void makeTransference(Account sender, Account reviecer, double amount);

    public List<Transference> getOperations(Account account);

}
