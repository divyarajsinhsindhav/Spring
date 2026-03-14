package com.tss.accounts.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AccountRequestDto {
    private Integer accountId;
    private Integer accountNumber;
    private String name;
    private Double balance;
    private String email;
}
