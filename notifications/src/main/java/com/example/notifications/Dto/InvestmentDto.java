package com.example.notifications.Dto;

import com.example.notifications.models.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentDto {

    private Long userId ;
    private Long companyId ;
    private String companyName ;
    private String type ;
    private double investmentAmount ;
    private double currentInvestmentAmount ;
    private Date startDate ;
    private  double duration ;
    private Status status = Status.IN_PROGRESS;
}
