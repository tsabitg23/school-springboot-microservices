package com.tsabitschool.schoolservice.repository;

import com.tsabitschool.schoolservice.model.School;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SchoolRepository extends MongoRepository<School, String> {
    Optional<School> findByName(String name);
}
