package com.tsabitschool.schoolservice;

import com.tsabitschool.schoolservice.model.School;
import com.tsabitschool.schoolservice.repository.SchoolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
@EnableDiscoveryClient
public class SchoolServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolServiceApplication.class, args);
	}

}
