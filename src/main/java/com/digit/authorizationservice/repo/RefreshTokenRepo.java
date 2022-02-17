package com.digit.authorizationservice.repo;

import com.digit.authorizationservice.model.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshTokenRepo extends MongoRepository<RefreshToken, String> {
    RefreshToken getRefreshTokenByValue(String value);
    void deleteRefreshTokenById(String id);
}
