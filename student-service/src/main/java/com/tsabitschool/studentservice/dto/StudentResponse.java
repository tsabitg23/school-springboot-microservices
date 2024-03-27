package com.tsabitschool.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private String studentId;
    private String school;
    private Integer gender;
    private LocalDate dob;
}
