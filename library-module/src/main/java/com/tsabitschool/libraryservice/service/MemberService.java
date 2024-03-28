package com.tsabitschool.libraryservice.service;

import com.tsabitschool.libraryservice.dto.MemberRequest;
import com.tsabitschool.libraryservice.model.Member;
import com.tsabitschool.libraryservice.repository.MemberRepository;
import com.tsabitschool.studentservice.dto.StudentResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final WebClient.Builder webClientBuilder;

    public void registerAsMember(MemberRequest memberRequest){
        if(isMemberAlreadyRegistered(memberRequest.getStudentId())){
            throw new IllegalArgumentException("Member already registered");
        }
        StudentResponse studentResponse = checkIfStudentExist(memberRequest.getStudentId());

        if(studentResponse == null){
            throw new IllegalArgumentException("Student not found");
        }
        Member member = new Member();
        member.setStudentId(memberRequest.getStudentId());
        member.setStudentName(studentResponse.getName());
        member.setActive(true);
        memberRepository.save(member);
    }

    private Boolean isMemberAlreadyRegistered(Long studentId){
        return memberRepository.existsByStudentId(studentId);
    }

    private StudentResponse checkIfStudentExist(Long studentId){
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("http://student-service/api/v1/students/"+studentId)
                    .retrieve()
                    .bodyToMono(StudentResponse.class)
                    .block();
        } catch (Exception e){
            log.error("Error: {}", e.getMessage());
            return null;
        }
    }
}
