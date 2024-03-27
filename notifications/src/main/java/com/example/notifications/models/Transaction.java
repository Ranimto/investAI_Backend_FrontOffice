package com.example.notifications.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "TRANSACTION_SEQUENCE" , allocationSize = 1)
    private Long id ;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private  Type type ;
    @Column(name = "amount")
    private double amount ;
    @Column(name = "recipient")
    private double recipient ;
    @Column(name = "status")
    private String status ;
    @Column(name = "currency")
    private String currency ;
    @Column(name = "RIB")
    private String RIB;

    @ManyToOne
    @JoinColumn(name = "bankAccount_id")
    private BankAccount bankAccount;


}
