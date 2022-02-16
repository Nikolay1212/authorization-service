package com.digit.authorizationservice.dto;

import com.digit.authorizationservice.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private String id;
    private String email;
    private String name;

    public static AccountDto from(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .name(account.getName())
                .build();
    }

    public static List<AccountDto> from(List<Account> accounts) {
        return accounts.stream().map(AccountDto::from).collect(Collectors.toList());
    }
}
