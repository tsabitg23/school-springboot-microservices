package com.tsabitschool.pensiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderTicketsResponse {
    private String code;
    private Double totalPrice;
    private Integer totalQuantity;
}
