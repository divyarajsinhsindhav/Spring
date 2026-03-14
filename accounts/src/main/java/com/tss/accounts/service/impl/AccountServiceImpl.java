package com.tss.accounts.service.impl;

import com.tss.accounts.dto.response.AccountPageDto;
import com.tss.accounts.dto.response.AccountResponseDto;
import com.tss.accounts.dto.response.AccountUpdateDto;
import com.tss.accounts.entity.Account;
import com.tss.accounts.mapper.AccountMapper;
import com.tss.accounts.repository.AccountRepository;
import com.tss.accounts.service.AccountService;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository,  AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountPageDto getAllAccounts(Integer pageNumber, Integer pageSize) {
        if (pageNumber < 0) pageNumber = 0;
        if (pageSize <= 0) pageSize = 10;
        try {

            Pageable pageable = PageRequest.of(pageNumber, pageSize,
                    Sort.by("accountNumber").ascending());

            Page<Account> accounts = accountRepository.findAll(pageable);

            Page<AccountResponseDto> dtoPage = accounts.map(accountMapper::toDto);

            return AccountPageDto.builder()
                    .accounts(dtoPage.getContent())
                    .noOfElements(dtoPage.getNumberOfElements())
                    .totalElements(dtoPage.getTotalElements())
                    .isFirst(dtoPage.isFirst())
                    .isLast(dtoPage.isLast())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching accounts", e);
        }
    }

    @Override
    public AccountResponseDto getAccountByAccountNumber(Integer accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NoResultException(
                        "Account number " + accountNumber + " does not exist"));

        return accountMapper.toDto(account);
    }

    @Override
    public AccountResponseDto createAccount(Account account) {
         try {
                Account createdAccount = accountRepository.save(account);
                return accountMapper.toDto(createdAccount);
         } catch (Exception e) {
                throw new RuntimeException("Account creation failed: " +  e.getMessage());
         }
    }

    @Override
    public void deleteAccountByAccountNumber(Integer accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NoResultException(
                        "Account number " + accountNumber + " does not exist"
                ));
        try {
            accountRepository.delete(account);
        } catch (Exception e) {
            throw new RuntimeException("Account deletion failed: " +  e.getMessage());
        }
    }

    public AccountResponseDto updateAccountByAccountNumber(Integer accountNumber, AccountUpdateDto dto) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NoResultException("Account number " + accountNumber + " does not exist"));

        try {
            if (dto.getEmail() != null) {
                account.setEmail(dto.getEmail());
            }

            if (dto.getPhone() != null) {
                account.setPhone(dto.getPhone());
            }

            Account updatedAccount = accountRepository.save(account);

            return accountMapper.toDto(updatedAccount);
        } catch (Exception e) {
            throw new RuntimeException("Account update failed: " +  e.getMessage());
        }
    }

}
