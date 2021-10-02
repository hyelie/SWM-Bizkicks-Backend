package com.bizkicks.backend.auth.security.config;

import com.bizkicks.backend.auth.security.exception.JwtAccessDeniedHandler;
import com.bizkicks.backend.auth.security.exception.JwtAuthenticationEntryPoint;
import com.bizkicks.backend.auth.security.jwt.JwtUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers("/member/**","/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**", "/admin/upload-csv", "/manage/measuredrate-price").permitAll()
                    .antMatchers("/manage/**", "/dashboard/**").hasRole("MANAGER")
                    .antMatchers("/kickboard/**").hasAnyRole("USER", "MANAGER")
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
                .apply(new JwtSecurityConfig(jwtUtil));
    }
}