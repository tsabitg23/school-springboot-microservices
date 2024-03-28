package com.tsabitschool.libraryservice.repository;

import com.tsabitschool.libraryservice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByStudentId(Long studentId);
}
