package com.tss.databaseconnection.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class StudentResponseDto {
    private int id;
    private String firstName;
    private int age;
}
