package com.example.ahimmoyakbackend.company.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.company.dto.CreateCompanyRequestDto;
import com.example.ahimmoyakbackend.company.dto.SearchCompanyRequestDto;
import com.example.ahimmoyakbackend.company.dto.UpdateCompanyRequestDto;
import com.example.ahimmoyakbackend.company.service.CompanyServiceImpl;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyServiceImpl companyServiceImpl;

    @PostMapping()
    public ResponseEntity<MessageResponseDto> createCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateCompanyRequestDto requestDto
    ) {
        return ResponseEntity.ok(companyServiceImpl.createCompany(userDetails, requestDto));
    }

    @PostMapping()
    public ResponseEntity<MessageResponseDto> searchCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody SearchCompanyRequestDto requestDto
    ) {
        return ResponseEntity.ok(companyServiceImpl.searchCompany(userDetails, requestDto));
    }

    @PatchMapping()
    public ResponseEntity<MessageResponseDto> updateCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long companyId,
            @RequestBody UpdateCompanyRequestDto requestDto
    ) {
        return ResponseEntity.ok(companyServiceImpl.updateCompany(userDetails, companyId, requestDto));
    }

    @DeleteMapping()
    public ResponseEntity<MessageResponseDto> deleteCompany(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long companyId
    ) {
        return ResponseEntity.ok(companyServiceImpl.deleteCompany(userDetails, companyId));
    }
}
