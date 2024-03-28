package com.tsabitschool.studentservice.repository;

import com.tsabitschool.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByDeletedAtIsNull();
}
