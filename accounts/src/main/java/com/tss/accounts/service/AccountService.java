package com.tss.accounts.service;

import com.tss.accounts.dto.response.AccountPageDto;
import com.tss.accounts.dto.response.AccountResponseDto;
import com.tss.accounts.entity.Account;

public interface AccountService {

    AccountPageDto getAllAccounts(Integer pageNumber, Integer pageSize);

    AccountResponseDto getAccountByAccountNumber(Integer accountNumber);

    AccountResponseDto createAccount(Account account);

    void deleteAccountByAccountNumber(Integer accountNumber);
}
