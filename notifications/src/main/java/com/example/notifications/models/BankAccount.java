package com.example.notifications.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data

public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "BANK_ACCOUNT_SEQUENCE" , allocationSize = 1)
    private  Long id;

    @Column(name = "glCode")
    private String glCode;
    @Column(name = "name")
    private String name ;
    @Column(name = "accountType")
    private  String accountType;
    @Column(name = "accountUsage")
    private String accountUsage;
    @Column(name = "usedAs")
    private String usedAs;
    @Column(name = "balance")
    private double balance ;
    @Column(name = "disabled")
    private boolean disabled ;
    @Column(name = "manualEntriesAllowed")
    private String manualEntriesAllowed ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @OneToMany(mappedBy = "bankAccount",  cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();


}
