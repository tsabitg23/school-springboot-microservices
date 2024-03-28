package com.tsabitschool.schoolservice.service;

import com.tsabitschool.schoolservice.dto.SchoolRequest;
import com.tsabitschool.schoolservice.dto.SchoolResponse;
import com.tsabitschool.schoolservice.dto.UpdateSchoolRequest;
import com.tsabitschool.schoolservice.model.School;
import com.tsabitschool.schoolservice.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolService {

    private final SchoolRepository schoolRepository;
    public void createSchool(SchoolRequest schoolRequest){
        School school = School.builder()
                .name(schoolRequest.getName())
                .build();

        schoolRepository.save(school);
        log.info("School {} created successfully", school.getId());
    }

    public List<SchoolResponse> getAllSchools() {
        List<School> schools = schoolRepository.findByDeletedAtIsNull();
        return schools.stream().map(this::mapToSchoolResponse).toList();
    }

    public void updateSchool(UpdateSchoolRequest schoolRequest){
        School school = schoolRepository.findById(schoolRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("School not found"));

        school.setName(schoolRequest.getName());
        schoolRepository.save(school);
        log.info("School {} updated successfully", school.getId());
    }

    public void deleteSchool(String schoolId){
        // show delete by adding deletedAt
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("School not found"));

        school.setDeletedAt(LocalDateTime.now());
        schoolRepository.save(school);
    }

    @Transactional(readOnly = true)
    @SneakyThrows
    public boolean isSchoolExist(String schoolName) {
        return schoolRepository.findByName(schoolName).isPresent();
    }

    private SchoolResponse mapToSchoolResponse(School school) {
        return SchoolResponse.builder()
                .id(school.getId())
                .name(school.getName())
                .deletedAt(school.getDeletedAt())
                .build();
    }
}
