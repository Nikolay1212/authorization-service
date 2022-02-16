package com.digit.authorizationservice.controller;

import com.digit.authorizationservice.dto.AuthDto;
import com.digit.authorizationservice.dto.TokenDto;
import com.digit.authorizationservice.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
public class SignInController {

    @Autowired
    private SignInService signInService;

    @PermitAll
    @PostMapping("/signIn")
    public TokenDto signIn(@RequestBody AuthDto auth) {
        return signInService.signIn(auth);
    }
}
