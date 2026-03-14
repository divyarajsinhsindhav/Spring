package com.tss.accounts.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@Builder
public class AccountPageDto {
    private List<AccountResponseDto> accounts;
    private int noOfElements;
    private long totalElements;
    private int totalPage;
    private boolean isFirst;
    private boolean isLast;
}
