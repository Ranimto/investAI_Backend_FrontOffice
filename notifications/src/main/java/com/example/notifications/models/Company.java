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
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "COMPANY_SEQUENCE" , allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name ;
    @Column(name = "activity")
    private  String activity ;
    @Column(name = "description")
    private  String description ;
    @Column(name = "address")
    private  String address ;
    @Column(name = "nbOfInvestors")
    private  double nbOfInvestors ;
    @Column(name = "debtRatio")
    private  double debtRatio ;
    @Column(name = "liquidityRatio")
    private  double liquidityRatio ;
    @Column(name = "interestCoverageRatio")
    private  double interestCoverageRatio ;
    @Column(name = "salesGrowthRatio")
    private  double salesGrowthRatio ;
    @Column(name = "profitabilityRatio")
    private  double profitabilityRatio ;

    @Column(name = "RIB", unique = true)
    private  double RIB ;
    @Column(name = "Balance")
    private  double Balance ;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Investment> investments = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Stock> stocks = new ArrayList<>();


}
