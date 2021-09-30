package com.bizkicks.backend.auth.security.config;

import com.bizkicks.backend.auth.security.jwt.JwtFilter;
import com.bizkicks.backend.auth.security.jwt.JwtUtil;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
    private final JwtUtil jwtUtil;

    @Override
    public void configure(HttpSecurity http){
        JwtFilter customJwtFilter = new JwtFilter(jwtUtil);
        http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
