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

    @PostMapping("/companies")
    public ResponseEntity<MessageResponseDto> createCompanyo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateCompanyRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(userDetails, requestDto));
    }

    @GetMapping("/companies")
    public ResponseEntity<List<SearchCompanyResponseDto>> searchCompany(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(companyService.searchCompany(name));
    }

    // 모르겠음
    @GetMapping("/companies/info")
    public ResponseEntity<CompanyDetailResponseDto> getCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(companyService.getCompany(userDetails));
    }

    // 모르겠음
    @GetMapping("/companies/emails/check")
    public ResponseEntity<CheckCompanyResponseDto> CheckCompanyEmail(
            @RequestParam String companyEmail,
            @RequestParam String userEmail
    ) {
        return ResponseEntity.ok(companyService.checkCompanyEmail(companyEmail, userEmail));
    }

    @GetMapping("/companies/affiliations")
    public ResponseEntity<MessageResponseDto> addAffiliation(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String companyName
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.addAffiliation(userDetails, companyName));
    }

    // 내가 회사 탈퇴
    @DeleteMapping("/companies/affiliations")
    public ResponseEntity<MessageResponseDto> detachCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.disconnectCompany(userDetails));
    }

    // supervisor 가 해당 유저를 회사에서 탈퇴
    @DeleteMapping("/companies/employees")
    public ResponseEntity<MessageResponseDto> deleteAffiliation(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String username
    ) {
        return ResponseEntity.ok(companyService.deleteAffiliation(userDetails, username));
    }


}
