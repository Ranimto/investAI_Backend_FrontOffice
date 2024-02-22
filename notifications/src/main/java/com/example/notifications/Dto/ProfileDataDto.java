package com.example.notifications.Dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDataDto {
    private String employmentStatus;
    private String age;
    private String investmentGoal;
    private String pastInvestmentExperience;
    private String financialMarketKnowledge;
    private String investmentProductKnowledge;
    private String investmentSelectionCriteria;
    private String avoidSpecificInvestments;
    private String annualIncomeRange;
    private String debts;
    private String investmentTimeframe;
    private String investmentStyle;
    private String targetRateOfReturn;
    private String preferredIndustries;
    private String liquidityPriority;
    private String reactionToMarketDownturn;
    private String comfortWithFluctuations;
    private String financialRiskComfortLevel;
    private String sourceOfFunds;

}
