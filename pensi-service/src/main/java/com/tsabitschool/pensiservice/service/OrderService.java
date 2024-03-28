package com.tsabitschool.pensiservice.service;

import com.tsabitschool.pensiservice.dto.EventResponse;
import com.tsabitschool.pensiservice.dto.OrderTicketsRequest;
import com.tsabitschool.pensiservice.dto.OrderTicketsResponse;
import com.tsabitschool.pensiservice.model.Event;
import com.tsabitschool.pensiservice.model.PurchaseOrder;
import com.tsabitschool.pensiservice.model.Ticket;
import com.tsabitschool.pensiservice.repository.OrderRepository;
import com.tsabitschool.studentservice.dto.StudentResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final EventService eventService;
    private final WebClient.Builder webClientBuilder;

    public OrderTicketsResponse orderTickets(OrderTicketsRequest orderTicketsRequest) {
        EventResponse eventResponse = eventService.getEventById(orderTicketsRequest.getEventId());
        Event event = mapToEvent(eventResponse);
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setEvent(event);
        String orderCode = UUID.randomUUID().toString();
        purchaseOrder.setCode(orderCode);
        purchaseOrder.setBuyerName(orderTicketsRequest.getBuyerName());
        purchaseOrder.setBuyerEmail(orderTicketsRequest.getBuyerEmail());
        purchaseOrder.setBuyerPhone(orderTicketsRequest.getBuyerPhone());
        purchaseOrder.setTotalQuantity(orderTicketsRequest.getQuantity());
        purchaseOrder.setTotalPrice(event.getPrice() * orderTicketsRequest.getQuantity());

        StudentResponse studentResponse = checkIfStudentExist(orderTicketsRequest.getStudentId());
        if(studentResponse == null){
            purchaseOrder.setStudentId(null);
        } else {
            purchaseOrder.setStudentId(studentResponse.getId());
        }
        List<Ticket> tickets = generateTicket(orderTicketsRequest.getQuantity(), purchaseOrder);
        purchaseOrder.setTickets(tickets);
        orderRepository.save(purchaseOrder);

        OrderTicketsResponse orderTicketsResponse = new OrderTicketsResponse();
        orderTicketsResponse.setCode(orderCode);
        orderTicketsResponse.setTotalPrice(purchaseOrder.getTotalPrice());
        orderTicketsResponse.setTotalQuantity(purchaseOrder.getTotalQuantity());
        return orderTicketsResponse;
    }

    private EventResponse getEvent(OrderTicketsRequest orderTicketsRequest) {
        try {
            return eventService.getEventById(orderTicketsRequest.getEventId());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private List<Ticket> generateTicket(Integer quantity, PurchaseOrder purchaseOrder) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = new Ticket();
            ticket.setCode(UUID.randomUUID().toString());
            ticket.setIsUsed(false);
            ticket.setPurchaseOrder(purchaseOrder);
            tickets.add(ticket);
        }
        return tickets;
    }

    private Event mapToEvent(EventResponse eventResponse) {
        Event event = new Event();
        event.setId(eventResponse.getId());
        event.setSchoolName(eventResponse.getSchoolName());
        event.setSchoolId(eventResponse.getSchoolId());
        event.setName(eventResponse.getName());
        event.setDescription(eventResponse.getDescription());
        event.setStartDate(eventResponse.getStartDate());
        event.setEndDate(eventResponse.getEndDate());
        event.setPrice(eventResponse.getPrice());
        event.setCreatedAt(eventResponse.getCreatedAt());
        event.setDeletedAt(eventResponse.getDeletedAt());

        return event;
    }

    private StudentResponse checkIfStudentExist(Long studentId){
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("http://student-service/api/v1/students/"+studentId)
                    .retrieve()
                    .bodyToMono(StudentResponse.class)
                    .block();
        } catch (Exception e){
            log.error("Error: {}", e.getMessage());
            return null;
        }
    }
}
