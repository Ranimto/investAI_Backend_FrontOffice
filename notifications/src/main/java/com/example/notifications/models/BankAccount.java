package com.example.notifications.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data

public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "BANK_ACCOUNT_SEQUENCE" , allocationSize = 1)
    private  Long id;

    @Column(name = "accountNo")
    private double accountNo;
    @Column(name = "bankName")
    private  String bankName;
    @Column(name = "isActive")
    private boolean isActive ;
    @Column(name = "currency")
    private String currency;
    @Column(name = "balance")
    private double balance ;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private AppUser user;



}
