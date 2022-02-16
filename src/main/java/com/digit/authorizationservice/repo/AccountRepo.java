package com.digit.authorizationservice.repo;

import com.digit.authorizationservice.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepo extends MongoRepository<Account, String> {
    Optional<Account> findAccountById(String id);
    Optional<Account> findAccountByEmail(String email);
}
