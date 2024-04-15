package com.example.notifications.models;

import com.example.notifications.Dto.BankAccountDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountResponse {

    @JsonProperty
    private List<BankAccountDto> pageItems;

}