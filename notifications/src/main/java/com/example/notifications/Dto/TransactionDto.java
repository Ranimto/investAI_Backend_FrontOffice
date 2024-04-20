package com.example.notifications.Dto;

import com.example.notifications.models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto implements Serializable {

    private Long fromAccountId;
    private Integer fromAccountType;
    private Long toOfficeId;
    private Long toClientId;
    private Integer toAccountType;
    private Long toAccountId;
    private double transferAmount;
    private String transferDate;
    private String transferDescription;
    private String locale;
    private String dateFormat;
    private Long fromClientId;
    private Long fromOfficeId;

    @JsonIgnore
    private Long bankAccountId;

}
