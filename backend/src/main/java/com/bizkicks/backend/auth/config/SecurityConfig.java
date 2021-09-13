package com.bizkicks.backend.auth.config;

import com.bizkicks.backend.auth.jwt.JwtAccessDeniedHandler;
import com.bizkicks.backend.auth.jwt.JwtAuthenticationEntryPoint;
import com.bizkicks.backend.auth.jwt.JwtRequestFilter;
import com.bizkicks.backend.exception.CustomException;
import com.bizkicks.backend.exception.ErrorCode;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers("/members/**").permitAll()
                    .antMatchers("/manage/**", "/dashboard/**").hasRole("MANAGER")
                    .antMatchers("/kickboard/**").hasAnyRole("USER", "MANAGER")
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and()
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
            .and()
                .exceptionHandling().accessDeniedHandler(new JwtAccessDeniedHandler())            
            .and()
                .addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
