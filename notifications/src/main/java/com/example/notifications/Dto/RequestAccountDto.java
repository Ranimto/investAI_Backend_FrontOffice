package com.example.notifications.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAccountDto {
    private Long id;
    private  String accountNo ;
    private  String status ;
    private  Long userId ;

}
