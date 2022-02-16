package com.digit.authorizationservice.service;

import com.digit.authorizationservice.dto.AccountDto;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAllAccounts();
}
