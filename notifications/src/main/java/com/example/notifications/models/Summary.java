package com.example.notifications.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Summary {

    private double totalDeposits;
    private double totalInterestEarned;
    private double totalInterestPosted;
    private double accountBalance;
    private double availableBalance;
}