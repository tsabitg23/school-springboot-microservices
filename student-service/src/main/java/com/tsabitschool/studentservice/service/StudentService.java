package com.tsabitschool.studentservice.service;

import com.tsabitschool.schoolservice.model.School;
import com.tsabitschool.studentservice.dto.StudentRequest;
import com.tsabitschool.studentservice.dto.StudentResponse;
import com.tsabitschool.studentservice.model.Student;
import com.tsabitschool.studentservice.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.tsabitschool.schoolservice.dto.SchoolResponse;
@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;
    private final WebClient.Builder webClientBuilder;
    public void createStudent(StudentRequest studentRequest){
        Student student = new Student();
        student.setStudentId(UUID.randomUUID().toString());
        student.setName(studentRequest.getName());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
        student.setSchoolId(studentRequest.getSchoolId());

        // Call school service
        SchoolResponse result = this.checkIfSchoolExist(studentRequest.getSchoolId());

        if(result != null){
            student.setSchool(result.getName());
            studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("School is not found, please enter correct school id");
        }

    }

    public List<StudentResponse> getAll() {
        List<Student> students = studentRepository.findByDeletedAtIsNull();
        return students.stream().map(this::mapToStudentResponse).toList();
    }

    public void updateStudent(Long studentId, StudentRequest studentRequest){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        if(student.getDeletedAt() != null){
            throw new IllegalArgumentException("Student already deleted");
        }

        student.setName(studentRequest.getName());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
        if(!student.getStudentId().isEmpty()){
            student.setSchoolId(studentRequest.getSchoolId());
            // Call school service
            SchoolResponse result = this.checkIfSchoolExist(studentRequest.getSchoolId());
            if(result != null){
                student.setSchool(result.getName());
                studentRepository.save(student);
            } else {
                throw new IllegalArgumentException("School is not found, please enter correct school id");
            }
        } else {
            studentRepository.save(student);
        }

    }

    public void deleteStudent(Long studentId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        if(student.getDeletedAt() != null){
            throw new IllegalArgumentException("Student already deleted");
        }

        student.setDeletedAt(LocalDateTime.now());
        studentRepository.save(student);
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .studentId(student.getStudentId())
                .name(student.getName())
                .dob(student.getDob())
                .school(student.getSchool())
                .schoolId(student.getSchoolId())
                .gender(student.getGender())
                .deletedAt(student.getDeletedAt())
                .build();
    }

    public StudentResponse getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        return mapToStudentResponse(student);
    }

    private SchoolResponse checkIfSchoolExist(String schoolId){
        try {
            return webClientBuilder.build().get()
                    .uri("http://school-service/api/v1/schools/" + schoolId)
                    .retrieve()
                    .bodyToMono(SchoolResponse.class)
                    .block();
        } catch (Exception e) {
            return null;
        }

    }
}
