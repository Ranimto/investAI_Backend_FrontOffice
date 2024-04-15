package com.example.notifications.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data

public class BankAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "BANK_ACCOUNT_SEQUENCE" , allocationSize = 1)
    private Long id;

    @Column(name = "accountNo")
    private String accountNo;

    @Column(name = "savingsProductName")
    private String savingsProductName;

     @Column(name = "summary")
     private  Summary summary;

    @Column(name = "active")
    private boolean active=false;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @OneToMany(mappedBy = "bankAccount",  cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

}
