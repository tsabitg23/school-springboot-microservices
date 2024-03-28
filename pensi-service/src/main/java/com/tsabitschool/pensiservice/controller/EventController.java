package com.tsabitschool.pensiservice.controller;


import com.tsabitschool.pensiservice.dto.EventRequest;
import com.tsabitschool.pensiservice.dto.EventResponse;
import com.tsabitschool.pensiservice.service.EventService;
import com.tsabitschool.schoolservice.dto.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pensi/event")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public ResponseEntity<GenericResponse> createNewEvent(@RequestBody EventRequest eventRequest) {
        try {
            eventService.createNewEvent(eventRequest);
            return new ResponseEntity<>(new GenericResponse("Event Created"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update event
    @PutMapping("/{eventId}")
    public ResponseEntity<GenericResponse> updateEvent(@PathVariable Long eventId, @RequestBody EventRequest eventRequest) {
        try {
            eventService.updateEvent(eventId, eventRequest);
            return new ResponseEntity<>(new GenericResponse("Event Updated"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete event
    @DeleteMapping("/{eventId}")
    public ResponseEntity<GenericResponse> deleteEvent(@PathVariable Long eventId) {
        try {
            eventService.deleteEvent(eventId);
            return new ResponseEntity<>(new GenericResponse("Event Deleted"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
