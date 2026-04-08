package com.amreshmaurya.bookstoreapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.httpBasic(h->h.disable()).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).formLogin(formLogin->formLogin.disable()).csrf(c->c.disable());
        return  httpSecurity.build();
    }
    
}
