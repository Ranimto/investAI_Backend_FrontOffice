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
public class BankAccountDto {
    private  Long id;
    private double accountNo;
    private  String bankName;
    private boolean isActive ;
    private String currency;
    private double balance ;
}
