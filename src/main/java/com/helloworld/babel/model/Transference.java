package com.helloworld.babel.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transference")
public class Transference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private double amount;
    @Column
    private String code;
    @Column
    private String senderAccount;
    @Column
    private String receiverAccount;
    @Column
    private String concept;

}
