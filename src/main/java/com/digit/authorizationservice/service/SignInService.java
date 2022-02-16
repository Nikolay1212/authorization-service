package com.digit.authorizationservice.service;

import com.digit.authorizationservice.dto.AuthDto;
import com.digit.authorizationservice.dto.TokenDto;

public interface SignInService {
    TokenDto signIn(AuthDto auth);
}
