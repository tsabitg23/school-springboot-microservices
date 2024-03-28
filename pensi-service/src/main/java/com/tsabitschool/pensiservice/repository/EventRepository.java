package com.tsabitschool.pensiservice.repository;

import com.tsabitschool.pensiservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByDeletedAtIsNull();
}
