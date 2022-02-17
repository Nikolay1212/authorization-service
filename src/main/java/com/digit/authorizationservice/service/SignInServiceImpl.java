package com.digit.authorizationservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.digit.authorizationservice.dto.AuthDto;
import com.digit.authorizationservice.dto.TokenDto;
import com.digit.authorizationservice.model.Account;
import com.digit.authorizationservice.model.RefreshToken;
import com.digit.authorizationservice.repo.AccountRepo;
import com.digit.authorizationservice.repo.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${refresh.token.time.unit}")
    private String timeUnitForRefreshToken;

    @Value("${refresh.token.expiration.time}")
    private Long expirationTimeForRefreshToken;

    @Value("${access.token.time.unit}")
    private String timeUnitForAccessToken;

    @Value("${access.token.expiration.time}")
    private Long expirationTimeForAccessToken;

    @Override
    public TokenDto signIn(AuthDto auth) {
        Optional<Account> accountOptional = accountRepo.findAccountByEmail(auth.getEmail());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (passwordEncoder.matches(auth.getPassword(), account.getHashPassword())) {

                //First Sign In

                if (account.getRefreshToken() == null) {
                    RefreshToken newRefreshToken = createNewRefreshToken(account);
                    refreshTokenRepo.save(newRefreshToken);
                    account.setRefreshToken(newRefreshToken.getValue());
                    accountRepo.save(account);
                    return new TokenDto(createNewAccessToken(account), newRefreshToken.getValue());
                }

                //Get Refresh Token and compare its expiration time

                RefreshToken refreshToken = refreshTokenRepo.getRefreshTokenByValue(account.getRefreshToken());
                if (refreshToken.getExpirationTime().isAfter(LocalDateTime.now())) {
                    return new TokenDto(createNewAccessToken(account), refreshToken.getValue());
                } else {
                    RefreshToken newRefreshToken = createNewRefreshToken(account);
                    refreshTokenRepo.save(newRefreshToken);
                    account.setRefreshToken(newRefreshToken.getValue());
                    accountRepo.save(account);
                    return new TokenDto(createNewAccessToken(account), newRefreshToken.getValue());
                }
            } else {
                throw new IllegalArgumentException("Wrong email/password");
            }
        } else {
            throw new IllegalArgumentException("Wrong email/password");
        }
    }
    private String createNewAccessToken(Account account) {
        return JWT.create()
                .withSubject(account.getId())
                .withClaim("email", account.getEmail())
                .withClaim("role", account.getRole().toString())
                .withClaim("state", account.getState().toString())
                .withClaim("time", LocalDateTime.now()
                        .plus(expirationTimeForAccessToken, ChronoUnit.valueOf(timeUnitForAccessToken)).toString())
                .sign(Algorithm.HMAC256(secretKey));
    }
    private RefreshToken createNewRefreshToken(Account account) {
        return RefreshToken.builder()
                .value(UUID.randomUUID().toString())
                .accountId(account.getId())
                .expirationTime(
                        LocalDateTime.now()
                                .plus(expirationTimeForRefreshToken, ChronoUnit.valueOf(timeUnitForRefreshToken)))
                .build();
    }
}
