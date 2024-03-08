package com.helloworld.babel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_name")
    private String userName;
    @Column
    private double income;


    public String getBankCode() {
        return this.accountNumber.substring(4, 8);
    }

    public void storeIncome(double income) {
        this.income += income;
    }

    public double withdrawIncome(double amount) {
        this.income -= amount;
        return this.income;
    }
}
