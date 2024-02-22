package com.example.notifications.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", sequenceName = "STOCK_SEQUENCE" , allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "open")
    private double open;

    @Column(name = "close")
    private double close;

    @Column(name = "high")
    private double high;

    @Column(name = "low")
    private double low;

    @Column(name = "volume")
    private double volume;


    @Column(name = "currentPrice")
    private double currentPrice;

    @Column(name = "interestRate")
    private double interestRate;

    @Column(name = "maturityDate")
    private LocalDateTime maturityDate;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
