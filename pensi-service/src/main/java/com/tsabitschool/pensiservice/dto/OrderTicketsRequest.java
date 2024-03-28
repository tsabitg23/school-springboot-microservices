package com.tsabitschool.pensiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderTicketsRequest {
    private Long eventId;
    private Long studentId;
    private Integer quantity;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
}
