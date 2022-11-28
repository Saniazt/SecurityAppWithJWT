package com.saniazt.springcourse.FirstSecurityApp.security;

import com.saniazt.springcourse.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    @Autowired
    private final PersonDetailsService personDetailsService;

    public AuthProviderImpl(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName(); //наша логика аутентификации

       UserDetails personDetails = personDetailsService.loadUserByUsername(username);
        String password = authentication.getCredentials().toString();

        if(!password.equals(personDetails.getPassword())) throw new BadCredentialsException("Incorrect password!");
        return new UsernamePasswordAuthenticationToken(personDetails,password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true; //показывает для какого обьекта работает провайдер, полезен когда много аут. провайдеров
    }
}
