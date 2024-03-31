package com.tsabitschool.pensiservice.service;

import com.tsabitschool.pensiservice.model.Ticket;
import com.tsabitschool.pensiservice.repository.OrderRepository;
import com.tsabitschool.pensiservice.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventService eventService;
    private final WebClient.Builder webClientBuilder;

    public void validateTicket(String ticketCode){
        Ticket validTicket = this.getValidTicketId(ticketCode);
        validTicket.setIsUsed(true);
        validTicket.setUsedAt(LocalDateTime.now());
        ticketRepository.save(validTicket);
    }

    private Ticket getValidTicketId(String ticketCode) {
        Optional<Ticket> ticket = ticketRepository.findByCode(ticketCode);
        if(ticket.isPresent()){
            Ticket ticketData = ticket.get();
            if(ticketData.getIsUsed()){
                throw new IllegalArgumentException("Ticket has been used");
            } else {
                return ticketData;
            }
        } else {
            throw new IllegalArgumentException("Ticket not found");
        }
    }
}
