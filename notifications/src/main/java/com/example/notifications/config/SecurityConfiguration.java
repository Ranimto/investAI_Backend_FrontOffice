package com.example.notifications.config;

import com.example.notifications.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.notifications.models.Permission;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{

        http

                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()
                .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                .requestMatchers(OPTIONS,"/admin/**").permitAll()
                .requestMatchers(POST,"/admin/**").hasAuthority(Permission.ADMIN_CREATE.name())//allow all the methods from this URL
                .requestMatchers(GET,"/admin/**").hasAuthority(Permission.ADMIN_READ.name())
                .requestMatchers(PUT,"/admin/**").hasAuthority(Permission.ADMIN_UPDATE.name())
                .requestMatchers(DELETE,"/**").hasAuthority(Permission.ADMIN_DELETE.name())
                .anyRequest()
                .authenticated()
                .and() // add a new configuration (ensure that every request should be authenticated)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//  create a session for each request
                .and()  //authentication provider
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) ;  //use jwt filter

        return http.build();


    }
}
