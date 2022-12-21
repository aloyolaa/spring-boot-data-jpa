package com.aloyolaa.springbootdatajpa.service;

import com.aloyolaa.springbootdatajpa.entity.User;
import com.aloyolaa.springbootdatajpa.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(JpaUserDetailService.class);

    public JpaUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority((authority.getName())))
                .collect(Collectors.toList());
        authorities.forEach((authority) -> {
            logger.info("Role: " + authority.getAuthority());
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
    }
}
