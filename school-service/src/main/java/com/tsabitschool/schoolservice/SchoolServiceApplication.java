package com.tsabitschool.schoolservice;

import com.tsabitschool.schoolservice.model.School;
import com.tsabitschool.schoolservice.repository.SchoolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class SchoolServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(SchoolRepository schoolRepository){
		return args -> {
			Optional<School> schoolOptional = schoolRepository.findByName("smk 1 bekasi");
			if(schoolOptional.isEmpty()){
				School school = new School();
				school.setName("smk 1 bekasi");
				schoolRepository.save(school);
			}
		};
	}

}
