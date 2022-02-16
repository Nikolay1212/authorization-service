package com.digit.authorizationservice.security.authentication;

import com.digit.authorizationservice.security.details.AccountUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthentication implements Authentication {

    private boolean isAuthenticated;
    private final String token;
    private String authority;
    private AccountUserDetails accountUserDetails;

    public void setAccountUserDetails(AccountUserDetails accountUserDetails) {
        this.accountUserDetails = accountUserDetails;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public JwtAuthentication(String token) {
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(authority));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return accountUserDetails;
    }

    @Override
    public Object getPrincipal() {
        return accountUserDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return token;
    }
}
