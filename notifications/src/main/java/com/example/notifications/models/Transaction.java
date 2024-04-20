package com.example.notifications.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Transaction implements  Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "TRANSACTION_SEQUENCE" , allocationSize = 1)
    private Integer id ;

    @Column(name = "fromAccountId")
    public Long fromAccountId;

    @Column(name = "fromAccountType")
    public Integer fromAccountType;

    @Column(name = "fromClientId")
    public Long fromClientId;

    @Column(name = "fromOfficeId")
    public Long fromOfficeId;

    @Column(name = "toOfficeId")
    public Long toOfficeId;

    @Column(name = "toClientId")
    public Long toClientId;

    @Column(name = "toAccountType")
    public Integer toAccountType;

    @Column(name = "toAccountId")
    public Long toAccountId;

    @Column(name = "transferAmount")
    public double transferAmount;

    @Column(name = "transferDate")
    public String transferDate;

    @Column(name = "transferDescription")
    public String transferDescription;

    @Column(name = "locale")
    public String locale;

    @Column(name = "dateFormat")
    public String dateFormat;


    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private  Type type ;

    @ManyToOne
    @JoinColumn(name = "bankAccount_id")
    private BankAccount bankAccount;


}
