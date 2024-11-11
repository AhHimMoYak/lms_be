package com.example.ahimmoyakbackend.company.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.FormerCompanyInfoRequestDto;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.company.service.CompanyService;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/company")
    public ResponseEntity<MessageResponseDto> createCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateCompanyRequestDto requestDto
    ) {
        return ResponseEntity.ok(companyService.createCompany(userDetails, requestDto));
    }

    @GetMapping("/company")
    public ResponseEntity<SearchCompanyResponseDto> searchCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String name
    ) {
        return ResponseEntity.ok(companyService.searchCompany(userDetails, name));
    }

    @PatchMapping("/company")
    public ResponseEntity<MessageResponseDto> updateCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long companyId,
            @RequestBody UpdateCompanyRequestDto requestDto
    ) {
        return ResponseEntity.ok(companyService.updateCompany(userDetails, companyId, requestDto));
    }

    @DeleteMapping("/company")
    public ResponseEntity<MessageResponseDto> deleteCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long companyId
    ) {
        return ResponseEntity.ok(companyService.deleteCompany(userDetails, companyId));
    }

    @GetMapping("/company/email/check")
    public ResponseEntity<MessageResponseDto> CheckCompanyEmail(
            @RequestParam String companyEmail
    ) {
        return ResponseEntity.ok(companyService.checkCompanyEmail(companyEmail));
    }

    @PostMapping("/company/affiliation")
    public ResponseEntity<MessageResponseDto> addAffiliation(
            @RequestBody AddAffiliationRequestDto requestDto
    ) {
        return ResponseEntity.ok(companyService.addAffiliation(requestDto));
    }

    @DeleteMapping("/company/affiliation")
    public ResponseEntity<MessageResponseDto> detachCompany(Long companyId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        MessageResponseDto responseDto = companyService.disconnectCompany(companyId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/company/employees")
    public ResponseEntity<GetEmployeeListResponseDto> getEmployeeList(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(companyService.getEmployeeList(userDetails));
    }

    // TODO deleteAffiliation
}
