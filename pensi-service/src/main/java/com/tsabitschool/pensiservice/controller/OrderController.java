package com.tsabitschool.pensiservice.controller;

import com.tsabitschool.pensiservice.dto.OrderTicketsRequest;
import com.tsabitschool.pensiservice.dto.OrderTicketsResponse;
import com.tsabitschool.pensiservice.repository.OrderRepository;
import com.tsabitschool.pensiservice.service.OrderService;
import com.tsabitschool.schoolservice.dto.GenericResponse;
import com.tsabitschool.schoolservice.dto.SchoolResponse;
import com.tsabitschool.studentservice.dto.StudentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pensi/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    // order ticket
    @PostMapping
    public ResponseEntity<?> orderTicket(@RequestBody OrderTicketsRequest orderTicketsRequest) {
        try {
            OrderTicketsResponse response = orderService.orderTickets(orderTicketsRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
