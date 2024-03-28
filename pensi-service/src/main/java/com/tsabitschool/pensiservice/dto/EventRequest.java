package com.tsabitschool.pensiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    private String schoolId;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;
}
