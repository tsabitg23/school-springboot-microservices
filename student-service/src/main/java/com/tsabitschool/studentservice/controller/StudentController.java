package com.tsabitschool.studentservice.controller;

import com.tsabitschool.studentservice.dto.StudentRequest;
import com.tsabitschool.studentservice.dto.StudentResponse;
import com.tsabitschool.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createStudent(@RequestBody StudentRequest studentRequest) {
        studentService.createStudent(studentRequest);
        return "Student created";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> getAllStudents(){
        return studentService.getAll();
    }
}
