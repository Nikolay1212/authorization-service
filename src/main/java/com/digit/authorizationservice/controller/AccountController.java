package com.digit.authorizationservice.controller;

import com.digit.authorizationservice.dto.AccountDto;
import com.digit.authorizationservice.dto.TokenDto;
import com.digit.authorizationservice.service.AccountService;
import com.digit.authorizationservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TokenService tokenService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountDto> getAllAccounts(@RequestHeader("Authorization") String auth) {
        TokenDto tokenDto = tokenService.checkToken(auth);
        if (auth.equals(tokenDto.getAccessToken())) {
            return accountService.getAllAccounts();
        } else {
            throw new BadCredentialsException("Bad token");
        }
    }
}
