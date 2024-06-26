package com.tsabitschool.schoolservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolResponse {
    private String id;
    private String name;
    private LocalDateTime deletedAt;
}
