package com.tss.databaseconnection.service;

import com.tss.databaseconnection.dto.request.StudentRequestDto;
import com.tss.databaseconnection.dto.response.StudentPageDto;
import com.tss.databaseconnection.dto.response.StudentResponseDto;
import com.tss.databaseconnection.entity.Student;
import com.tss.databaseconnection.mapper.StudentMapper;
import com.tss.databaseconnection.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository,  StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentPageDto findAll(Integer pageSize, Integer pageNumber) {

        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        Page<Student> students = studentRepository.findAll(pageable);

        Page<StudentResponseDto> dtoPage = students.map(studentMapper::toDto);

        return StudentPageDto.builder()
                .content(dtoPage.getContent())
                .noOfElements(dtoPage.getNumberOfElements())
                .totalElements(dtoPage.getTotalElements())
                .totalPage(dtoPage.getTotalPages())
                .isFirst(dtoPage.isFirst())
                .isLast(dtoPage.isLast())
                .build();
    }

    @Override
    public StudentResponseDto findById(int id) {
        Student student = studentRepository.findById(id).get();
        return studentMapper.toDto(student);
    }

    @Override
    public StudentResponseDto addStudent(StudentRequestDto student) {
        Student newStudent = studentMapper.toEntity(student);
        studentRepository.save(newStudent);
        return studentMapper.toDto(newStudent);
    }

    @Override
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    @Override
    public StudentResponseDto updateStudent(int id, StudentRequestDto updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFirstName(updatedStudent.getFirstName());
        student.setAge(updatedStudent.getAge());
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDto(savedStudent);
    }

    @Override
    public StudentPageDto findByFirstName(String firstName, Integer pageSize, Integer pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Student> students = studentRepository.findByFirstName(firstName, pageable);

        Page<StudentResponseDto> dtoPage = students.map(studentMapper::toDto);

        return StudentPageDto.builder()
                .content(dtoPage.getContent())
                .noOfElements(dtoPage.getNumberOfElements())
                .totalElements(dtoPage.getTotalElements())
                .totalPage(dtoPage.getTotalPages())
                .isFirst(dtoPage.isFirst())
                .isLast(dtoPage.isLast())
                .build();
    }
}
