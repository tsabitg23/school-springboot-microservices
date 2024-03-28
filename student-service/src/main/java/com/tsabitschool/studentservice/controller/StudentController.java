package com.tsabitschool.studentservice.controller;

import com.tsabitschool.studentservice.dto.GenericResponse;
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
    public ResponseEntity<GenericResponse> createStudent(@RequestBody StudentRequest studentRequest) {
        try {
            studentService.createStudent(studentRequest);
            return new ResponseEntity<>(new GenericResponse("Student created"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<GenericResponse> updateStudent(@PathVariable Long studentId, @RequestBody StudentRequest studentRequest) {
        try {
            studentService.updateStudent(studentId, studentRequest);
            return new ResponseEntity<>(new GenericResponse("Student updated"), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<GenericResponse> deleteStudent(@PathVariable Long studentId) {
        try {
            studentService.deleteStudent(studentId);
            return new ResponseEntity<>(new GenericResponse("Student deleted"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> getAllStudents(){
        return studentService.getAll();
    }
}
