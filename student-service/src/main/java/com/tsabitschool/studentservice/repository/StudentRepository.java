package com.tsabitschool.studentservice.repository;

import com.tsabitschool.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
