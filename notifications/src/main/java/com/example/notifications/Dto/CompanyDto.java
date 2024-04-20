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
public class CompanyDto {
    private Long id;
    private String name ;
    private  String activity ;
    private  String description ;
    private  String address ;
    private  double nbOfInvestors ;
    private  double debtRatio ;
    private  double liquidityRatio ;
    private  double interestCoverageRatio ;
    private  double salesGrowthRatio ;
    private  double profitabilityRatio ;
    private  double RIB ;
    private  double Balance ;
    private double accountType;
}
