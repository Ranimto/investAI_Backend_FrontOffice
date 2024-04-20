package com.example.notifications.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProfileData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "PROFILE_DATA_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(name = "employmentStatus")
    private String employmentStatus;
    @Column(name = "age")
    private String age;
    @Column(name = "investmentGoal")
    private String investmentGoal;
    @Column(name = "pastInvestmentExperience")
    private String pastInvestmentExperience;
    @Column(name = "financialMarketKnowledge")
    private String financialMarketKnowledge;
    @Column(name = "investmentProductKnowledge")
    private String investmentProductKnowledge;
    @Column(name = "investmentSelectionCriteria")
    private String investmentSelectionCriteria;
    @Column(name = "avoidSpecificInvestments")
    private String avoidSpecificInvestments;
    @Column(name = "annualIncomeRange")
    private String annualIncomeRange;
    @Column(name = "debts")
    private String debts;
    @Column(name = "investmentTimeframe")
    private String investmentTimeframe;
    @Column(name = "investmentStyle")
    private String investmentStyle;
    @Column(name = "targetRateOfReturn")
    private String targetRateOfReturn;
    @Column(name = " preferredIndustries")
    private String preferredIndustries;
    @Column(name = "liquidityPriority")
    private String liquidityPriority;
    @Column(name = "reactionToMarketDownturn")
    private String reactionToMarketDownturn;
    @Column(name = "comfortWithFluctuations")
    private String comfortWithFluctuations;
    @Column(name = "financialRiskComfortLevel")
    private String financialRiskComfortLevel;
    @Column(name = "sourceOfFunds")
    private String sourceOfFunds;


    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
}
