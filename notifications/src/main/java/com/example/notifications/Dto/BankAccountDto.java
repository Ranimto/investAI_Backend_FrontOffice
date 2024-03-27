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
    private String glCode;
    private  String name;
    private String accountType ;
    private String accountUsage;
    private String usedAs ;
    private double balance ;
    private Long userId ;
    private boolean disabled ;
    private String manualEntriesAllowed ;
}
