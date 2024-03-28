package com.tsabitschool.pensiservice.service;

import com.tsabitschool.pensiservice.dto.EventRequest;
import com.tsabitschool.pensiservice.dto.EventResponse;
import com.tsabitschool.pensiservice.model.Event;
import com.tsabitschool.pensiservice.repository.EventRepository;
import com.tsabitschool.schoolservice.dto.SchoolResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final WebClient.Builder webClientBuilder;

    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findByDeletedAtIsNull();
        return events.stream().map(this::mapToEventResponse).toList();
    }

    // get event by id
    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        return mapToEventResponse(event);
    }

    private EventResponse mapToEventResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .schoolName(event.getSchoolName())
                .schoolId(event.getSchoolId())
                .name(event.getName())
                .description(event.getDescription())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .price(event.getPrice())
                .createdAt(event.getCreatedAt())
                .deletedAt(event.getDeletedAt())
                .build();
    }

    public void createNewEvent(EventRequest eventRequest) {
        SchoolResponse school = checkIfSchoolExist(eventRequest.getSchoolId());
        if(school == null){
            throw new IllegalArgumentException("School is not found, please enter correct school id");
        }

        // check if start date must be earlier than end date
        if(eventRequest.getStartDate().isAfter(eventRequest.getEndDate())){
            throw new IllegalArgumentException("Start date must be earlier than end date");
        }

        Event event = new Event();
        event.setSchoolName(school.getName());
        event.setSchoolId(school.getId());
        event.setName(eventRequest.getName());
        event.setDescription(eventRequest.getDescription());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        event.setPrice(eventRequest.getPrice());
        eventRepository.save(event);
    }

    public void updateEvent(Long id, EventRequest eventRequest) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if(event.getDeletedAt() != null){
            throw new IllegalArgumentException("Event already deleted");
        }

        SchoolResponse school = checkIfSchoolExist(eventRequest.getSchoolId());
        if(school == null){
            throw new IllegalArgumentException("School is not found, please enter correct school id");
        }

        // check if start date must be earlier than end date
        if(eventRequest.getStartDate().isAfter(eventRequest.getEndDate())){
            throw new IllegalArgumentException("Start date must be earlier than end date");
        }

        event.setSchoolName(school.getName());
        event.setSchoolId(school.getId());
        event.setName(eventRequest.getName());
        event.setDescription(eventRequest.getDescription());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        event.setPrice(eventRequest.getPrice());
        eventRepository.save(event);
    }

    // Delete event by id
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        if(event.getDeletedAt() != null){
            throw new IllegalArgumentException("Event already deleted");
        }
        event.setDeletedAt(LocalDateTime.now());
        eventRepository.save(event);
    }

    private SchoolResponse checkIfSchoolExist(String schoolId){
        try {
            return webClientBuilder.build().get()
                    .uri("http://school-service/api/v1/schools/" + schoolId)
                    .retrieve()
                    .bodyToMono(SchoolResponse.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
}
