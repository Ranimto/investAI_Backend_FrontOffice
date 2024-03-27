package com.example.notifications.Dto;

import com.example.notifications.models.BankAccount;
import com.example.notifications.models.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Type type ;
    private double amount ;
    private double recipient ;
    private String status ;
    private String currency ;
    private double RIB;
    private Long bankAccountId ;
}
