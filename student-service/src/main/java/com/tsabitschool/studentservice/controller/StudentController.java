package com.tsabitschool.studentservice.controller;

import com.tsabitschool.studentservice.dto.StudentRequest;
import com.tsabitschool.studentservice.dto.StudentResponse;
import com.tsabitschool.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;
    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody StudentRequest studentRequest) {
        try {
            studentService.createStudent(studentRequest);
            return new ResponseEntity<>("Student created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> getAllStudents(){
        return studentService.getAll();
    }
}
