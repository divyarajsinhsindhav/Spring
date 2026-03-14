package com.tss.databaseconnection.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class StudentRequestDto {

    private String firstName;
    private int age;

}
