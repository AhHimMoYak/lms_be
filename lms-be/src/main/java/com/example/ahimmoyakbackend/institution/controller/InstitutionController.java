package com.example.ahimmoyakbackend.institution.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionResponseDto;
import com.example.ahimmoyakbackend.institution.dto.UserInstitutionIdResponseDto;
import com.example.ahimmoyakbackend.institution.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class InstitutionController {

    private final InstitutionService institutionServiceImpl;

    @PostMapping()
    public ResponseEntity<String> createInstitution(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateInstitutionRequestDto requestDto
    ){
        return institutionServiceImpl.createInstitution(userDetails, requestDto)
                ? ResponseEntity.ok("회사 등록 성공")
                : ResponseEntity.badRequest().body("회사 등록 실패");
    }

    @GetMapping()
    public ResponseEntity<CreateInstitutionResponseDto> getInstitutionDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.ok(institutionServiceImpl.getInstitutionDetail(userDetails));
    }

    @PatchMapping()
    public ResponseEntity<String> updateInstitution(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateInstitutionRequestDto requestDto
    ){
        return institutionServiceImpl.updateInstitution(userDetails, requestDto)
                ? ResponseEntity.ok("회사 수정 성공")
                : ResponseEntity.badRequest().body("회사 수정 실패");
    }

    @GetMapping()
    public ResponseEntity<UserInstitutionIdResponseDto> getInstitutionId(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.ok(institutionServiceImpl.getInstitutionId(userDetails));
    }

}
