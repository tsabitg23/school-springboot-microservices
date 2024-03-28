package com.tsabitschool.schoolservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "school")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class School {
    @Id
    private String id;
    private String name;
    private LocalDateTime deletedAt;
}
