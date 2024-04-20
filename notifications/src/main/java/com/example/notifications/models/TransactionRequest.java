package com.example.notifications.models;

import com.example.notifications.Dto.TransactionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest implements Serializable {

   private String fromAccountNo;
   private String toAccountNo;
   private double amount ;



}