package com.example.notifications.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Investment {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "INVESTMENT_SEQUENCE" , allocationSize = 1)
    @GenericGenerator(name = "generator", strategy = "com.example.notifications.models.CustomInvestIdGenerator")
    private InvestId id ;

    @Column(name = "type")
    private String type ;
    @Column(name = "investmentAmount")
    private double investmentAmount ;
    @Column(name = "currentInvestmentAmount")
    private  double currentInvestmentAmount ;
    @Column(name = "startDate")
    private Date startDate ;
    @Column(name = "duration")
    private  double duration ;
    @Column(name = "status")
    private  String status ;
    @Column(name = "companyName")
    private  String companyName ;
    @Column(name = "dividendPayout")
    private  double dividendPayout ;


    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private Company company;

}
