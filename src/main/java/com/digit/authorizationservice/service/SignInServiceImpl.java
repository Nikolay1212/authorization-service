package com.digit.authorizationservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.digit.authorizationservice.dto.AuthDto;
import com.digit.authorizationservice.dto.TokenDto;
import com.digit.authorizationservice.model.Account;
import com.digit.authorizationservice.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public TokenDto signIn(AuthDto auth) {
        Optional<Account> accountOptional = accountRepo.findAccountByEmail(auth.getEmail());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (passwordEncoder.matches(auth.getPassword(), account.getHashPassword())) {
                String token = JWT.create()
                        .withSubject(account.getId())
                        .withClaim("email", account.getEmail())
                        .withClaim("role", account.getRole().toString())
                        .withClaim("state", account.getState().toString())
                        .sign(Algorithm.HMAC256(secretKey));
                return new TokenDto(token);
            } else {
                throw new IllegalArgumentException("Wrong email/password");
            }
        } else {
            throw new IllegalArgumentException("Wrong email/password");
        }
    }
}
