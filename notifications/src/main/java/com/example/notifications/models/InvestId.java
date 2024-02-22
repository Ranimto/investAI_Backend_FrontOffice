package com.example.notifications.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class InvestId implements Serializable {
    private Long userId ;
    private Long companyId ;
}
