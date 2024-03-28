package com.tsabitschool.libraryservice.controller;

import com.tsabitschool.libraryservice.dto.MemberRequest;
import com.tsabitschool.libraryservice.service.MemberService;
import com.tsabitschool.schoolservice.dto.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
@Slf4j
public class LibraryController {
    private final MemberService memberService;
    @PostMapping
    public ResponseEntity<GenericResponse> registerAsMember(@RequestBody MemberRequest memberRequest) {
        try {
            memberService.registerAsMember(memberRequest);
            return new ResponseEntity<>(new GenericResponse("Member registered"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
