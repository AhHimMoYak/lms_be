package com.example.ahimmoyakbackend.company.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.company.service.CompanyService;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/company")
    public ResponseEntity<MessageResponseDto> createCompany(
            //test 5
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateCompanyRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(userDetails, requestDto));
    }

    @GetMapping("/company")
    public ResponseEntity<List<SearchCompanyResponseDto>> searchCompany(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(companyService.searchCompany(name));
    }

    @GetMapping("/company/info")
    public ResponseEntity<CompanyDetailResponseDto> getCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(companyService.getCompany(userDetails));
    }

    @PatchMapping("/company")
    public ResponseEntity<MessageResponseDto> updateCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String name,
            @RequestBody UpdateCompanyRequestDto requestDto
    ) {
        return ResponseEntity.ok(companyService.updateCompany(userDetails, name, requestDto));
    }

    @GetMapping("/company/email/check")
    public ResponseEntity<CheckCompanyResponseDto> CheckCompanyEmail(
            @RequestParam String companyEmail,
            @RequestParam String userEmail
    ) {
        return ResponseEntity.ok(companyService.checkCompanyEmail(companyEmail, userEmail));
    }

    @GetMapping("/company/affiliation")
    public ResponseEntity<MessageResponseDto> addAffiliation(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String companyName
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.addAffiliation(userDetails, companyName));
    }

    // 내가 회사 탈퇴
    @DeleteMapping("/company/affiliation")
    public ResponseEntity<MessageResponseDto> detachCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.disconnectCompany(userDetails));
    }

    // supervisor 가 해당 유저를 회사에서 탈퇴
    @DeleteMapping("/company/employees")
    public ResponseEntity<MessageResponseDto> deleteAffiliation(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String username
    ) {
        return ResponseEntity.ok(companyService.deleteAffiliation(userDetails, username));
    }

    @GetMapping("/company/employees")
    public ResponseEntity<List<GetEmployeeListResponseDto>> getEmployeeList(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(companyService.getEmployeeList(userDetails));
    }

    // courseProvide
    @PostMapping("/company/courseProvide")
    public ResponseEntity<MessageResponseDto> CreateCourseProvide(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long courseId,
            @RequestBody CreateCourseProvideRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCourseProvider(userDetails,courseId,requestDto));
    }

    @GetMapping("/company/courseProvide/list")
    public ResponseEntity<List<CourseProvideListResponseDto>> getCourseProvideList(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(companyService.getCourseProvideList(userDetails));
    }

    @PostMapping("/company/courseProvide/employees")
    public ResponseEntity<MessageResponseDto> submitEmployeeListForEnrollment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody submitEmployeeListRequestDto requestDto
    ) {
        return ResponseEntity.ok(companyService.submitEmployeeListForEnrollment(userDetails,requestDto));
    }

}
