package com.tss.accounts.mapper;

import com.tss.accounts.dto.request.AccountRequestDto;
import com.tss.accounts.dto.response.AccountResponseDto;
import com.tss.accounts.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(AccountRequestDto accountRequestDto);
    AccountResponseDto toDto(Account account);
}
