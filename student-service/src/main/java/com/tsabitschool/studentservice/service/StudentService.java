package com.tsabitschool.studentservice.service;

import com.tsabitschool.studentservice.dto.StudentRequest;
import com.tsabitschool.studentservice.dto.StudentResponse;
import com.tsabitschool.studentservice.model.Student;
import com.tsabitschool.studentservice.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;
    private final WebClient webClient;
    public void createStudent(StudentRequest studentRequest){
        Student student = new Student();
        student.setStudentId(UUID.randomUUID().toString());
        student.setName(studentRequest.getName());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
        student.setSchool(studentRequest.getSchool());

        // Call school service
        Boolean result = webClient.get()
                .uri("http://localhost:8080/api/v1/schools/" + studentRequest.getSchool())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if(Boolean.TRUE.equals(result)){
            studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("School is not found, please enter correct school name");
        }

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
