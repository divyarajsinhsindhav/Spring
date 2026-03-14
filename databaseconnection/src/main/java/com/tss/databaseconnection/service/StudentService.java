package com.tss.databaseconnection.service;

import com.tss.databaseconnection.dto.request.StudentRequestDto;
import com.tss.databaseconnection.dto.response.StudentPageDto;
import com.tss.databaseconnection.dto.response.StudentResponseDto;
import com.tss.databaseconnection.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    StudentPageDto findAll(Integer pageSize, Integer pageNumber);

    StudentResponseDto findById(int id);

    StudentResponseDto addStudent(StudentRequestDto student);

    void deleteStudent(int id);

    StudentResponseDto updateStudent(int id, StudentRequestDto updatedStudent);

    StudentPageDto findByFirstName(String firstName, Integer pageSize, Integer pageNumber);
}
