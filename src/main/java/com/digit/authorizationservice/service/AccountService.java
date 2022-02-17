package com.digit.authorizationservice.service;

import com.digit.authorizationservice.dto.AccountDto;
import com.digit.authorizationservice.model.Account;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAllAccounts();
    Account getAccountById(String id);
}
