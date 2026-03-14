package com.tss.databaseconnection.mapper;

import com.tss.databaseconnection.dto.request.StudentRequestDto;
import com.tss.databaseconnection.dto.response.StudentPageDto;
import com.tss.databaseconnection.dto.response.StudentResponseDto;
import com.tss.databaseconnection.entity.Student;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentRequestDto studentRequestDto);
    StudentResponseDto toDto(Student student);
    StudentPageDto toPageDto(Page<Student> studentPage);
}
