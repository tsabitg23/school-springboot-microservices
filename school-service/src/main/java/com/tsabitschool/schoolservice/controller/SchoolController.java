package com.tsabitschool.schoolservice.controller;

import com.tsabitschool.schoolservice.dto.SchoolRequest;
import com.tsabitschool.schoolservice.dto.SchoolResponse;
import com.tsabitschool.schoolservice.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSchool(@RequestBody SchoolRequest schoolRequest) {
        schoolService.createSchool(schoolRequest);
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
}
