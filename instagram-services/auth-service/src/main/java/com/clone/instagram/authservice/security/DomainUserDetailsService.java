package com.clone.instagram.authservice.security;

import com.clone.instagram.authservice.model.Role;
import com.clone.instagram.authservice.model.User;
import com.clone.instagram.authservice.repository.UserRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        if (new EmailValidator().isValid(login, null)) {
            return userRepository
                    .findByUsername(login)
                    .map(user -> createSpringSecurityUser(login, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository
                .findByUsername(lowercaseLogin)
                .map(user -> createSpringSecurityUser(lowercaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        List<SimpleGrantedAuthority> grantedAuthorities = user
                .getRoles()
                .stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
