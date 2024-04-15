package com.example.notifications.Dto;
import com.example.notifications.models.Summary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {
    private Long id;
    private String accountNo;
    private String savingsProductName;
    private double totalDeposits;
   private Summary summary;
    private boolean active;
    private Long userId;


}
