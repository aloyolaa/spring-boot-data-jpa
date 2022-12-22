package com.aloyolaa.springbootdatajpa.config;

import com.aloyolaa.springbootdatajpa.auth.LoginSuccess;
import com.aloyolaa.springbootdatajpa.service.JpaUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final LoginSuccess loginSuccess;
    private final JpaUserDetailService jpaUserDetailService;

    public SecurityConfig(LoginSuccess loginSuccess, JpaUserDetailService jpaUserDetailService) {
        this.loginSuccess = loginSuccess;
        this.jpaUserDetailService = jpaUserDetailService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception {
        return security.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(jpaUserDetailService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/customers/", "/customers/find-all", "/locale", "/css/**", "/js/**").permitAll()
                                .anyRequest().authenticated()
                ).formLogin().successHandler(loginSuccess).loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedPage("/error-403")
                .and().build();
    }

}
