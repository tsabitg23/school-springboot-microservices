package com.tsabitschool.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private String name;
    private String school;
    private Integer gender;
    private LocalDate dob;
}
