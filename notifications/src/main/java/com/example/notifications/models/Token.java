package com.example.notifications.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "TOKEN_SEQUENCE" , allocationSize = 1)
    public Long id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false, length = 10)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public AppUser user;

}

