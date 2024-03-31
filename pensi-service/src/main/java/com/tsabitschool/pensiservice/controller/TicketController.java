package com.tsabitschool.pensiservice.controller;

import com.tsabitschool.pensiservice.service.OrderService;
import com.tsabitschool.pensiservice.service.TicketService;
import com.tsabitschool.schoolservice.dto.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pensi/ticket")
@RequiredArgsConstructor
@Slf4j
public class TicketController {
    private final TicketService ticketService;

    // validate ticket
    @PostMapping("/{ticketCode}/validate")
    public ResponseEntity<?> validateTicket(@PathVariable String ticketCode) {
        try {
            ticketService.validateTicket(ticketCode);
            return new ResponseEntity<>(new GenericResponse("Ticket validated"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
