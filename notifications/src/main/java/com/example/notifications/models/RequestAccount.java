package com.example.notifications.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "REQUEST_ACCOUNT_SEQUENCE", allocationSize = 1)
    private Long id;
    @Column(name = "accountNo")
    private  String accountNo ;
    @Column(name = "status")
    private  String status ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;


}
