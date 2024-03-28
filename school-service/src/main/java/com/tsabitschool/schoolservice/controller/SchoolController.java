package com.tsabitschool.schoolservice.controller;

import com.tsabitschool.schoolservice.dto.GenericResponse;
import com.tsabitschool.schoolservice.dto.SchoolRequest;
import com.tsabitschool.schoolservice.dto.SchoolResponse;
import com.tsabitschool.schoolservice.dto.UpdateSchoolRequest;
import com.tsabitschool.schoolservice.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse createSchool(@RequestBody SchoolRequest schoolRequest) {
        schoolService.createSchool(schoolRequest);
        return new GenericResponse("School created successfully");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SchoolResponse> getAllSchools(){
        return schoolService.getAllSchools();
    }

    @GetMapping("/{school-name}")
    public boolean isSchoolExist(@PathVariable("school-name") String schoolName) {
        return schoolService.isSchoolExist(schoolName);
    }

    @PutMapping("/{school-id}")
    public ResponseEntity<GenericResponse> updateSchool(@PathVariable("school-id") String schoolId, @RequestBody SchoolRequest schoolRequest) {
        try {
            UpdateSchoolRequest updateSchoolRequest = new UpdateSchoolRequest(schoolId, schoolRequest.getName());
            schoolService.updateSchool(updateSchoolRequest);
            return ResponseEntity.ok(new GenericResponse("School updated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{school-id}")
    public ResponseEntity<GenericResponse> deleteSchool(@PathVariable("school-id") String schoolId) {
        try {
            schoolService.deleteSchool(schoolId);
            return ResponseEntity.ok(new GenericResponse("School deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse(e.getMessage()));
        }
    }
}
