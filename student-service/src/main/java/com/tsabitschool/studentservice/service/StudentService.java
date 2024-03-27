package com.tsabitschool.studentservice.service;

import com.tsabitschool.studentservice.dto.StudentRequest;
import com.tsabitschool.studentservice.dto.StudentResponse;
import com.tsabitschool.studentservice.model.Student;
import com.tsabitschool.studentservice.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;
    public void createStudent(StudentRequest studentRequest){
        Student student = new Student();
        student.setStudentId(UUID.randomUUID().toString());
        student.setName(studentRequest.getName());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
        student.setSchool(studentRequest.getSchool());

        studentRepository.save(student);
    }

    public List<StudentResponse> getAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::mapToStudentResponse).toList();
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .studentId(student.getStudentId())
                .name(student.getName())
                .dob(student.getDob())
                .school(student.getSchool())
                .gender(student.getGender())
                .build();
    }
}
