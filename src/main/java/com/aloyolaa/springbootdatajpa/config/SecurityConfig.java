package com.aloyolaa.springbootdatajpa.config;

import com.aloyolaa.springbootdatajpa.auth.LoginSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";
    @Autowired
    private LoginSuccess loginSuccess;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("12345")).roles(ADMIN, USER).build());
        manager.createUser(User.withUsername("aloyolaa").password(passwordEncoder().encode("01243411")).roles(USER).build());
        return manager;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception {
        return security.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/customers/", "/customers/find-all", "/css/**", "/js/**").permitAll()
                                //.requestMatchers("/customers/detail/**").hasAnyRole(USER)
                                //.requestMatchers("/customers/form/**").hasAnyRole(ADMIN)
                                //.requestMatchers("/customers/delete/**").hasAnyRole(ADMIN)
                                //.requestMatchers("/invoices/**").hasAnyRole(ADMIN)
                                .anyRequest().authenticated()
                ).formLogin().successHandler(loginSuccess).loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedPage("/error-403")
                .and().build();
    }

}
