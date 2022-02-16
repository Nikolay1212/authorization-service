package com.digit.authorizationservice.service;

import com.digit.authorizationservice.dto.AccountDto;
import com.digit.authorizationservice.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public List<AccountDto> getAllAccounts() {
        return AccountDto.from(accountRepo.findAll());
    }
}
