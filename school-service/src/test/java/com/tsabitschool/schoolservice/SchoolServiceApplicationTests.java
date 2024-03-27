package com.tsabitschool.schoolservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsabitschool.schoolservice.dto.SchoolRequest;
import com.tsabitschool.schoolservice.repository.SchoolRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class SchoolServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.7");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SchoolRepository schoolRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}
	@Test
	void shouldCreateSchool() throws Exception {
		SchoolRequest schoolRequest = getSchoolRequest();
		String schoolRequestString = objectMapper.writeValueAsString(schoolRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/schools")
				.contentType(MediaType.APPLICATION_JSON)
				.content(schoolRequestString))
				.andExpect(status().isCreated());
        Assertions.assertFalse(schoolRepository.findAll().isEmpty());
	}

	private SchoolRequest getSchoolRequest(){
		return SchoolRequest.builder()
				.name("Test School")
				.build();
	}
}
