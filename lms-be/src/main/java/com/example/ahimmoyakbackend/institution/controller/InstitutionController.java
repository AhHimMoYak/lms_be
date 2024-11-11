package com.example.ahimmoyakbackend.institution.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.institution.dto.GetInstitutionDetailRequestDto;
import com.example.ahimmoyakbackend.institution.dto.UpdateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.UserInstitutionIdResponseDto;
import com.example.ahimmoyakbackend.institution.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/institution")
public class InstitutionController {

    private final InstitutionService institutionService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> createInstitution(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateInstitutionRequestDto requestDto
    ){
        return ResponseEntity.ok(institutionService.createInstitution(userDetails, requestDto));
    }

    @GetMapping
    public ResponseEntity<GetInstitutionDetailRequestDto> getInstitutionDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(institutionService.getInstitutionDetail(userDetails));
    }

    @GetMapping("/{institutionId}")
    public ResponseEntity<GetInstitutionDetailRequestDto> getInstitutionDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long institutionId){
        return ResponseEntity.ok(institutionService.getInstitutionDetail(userDetails, institutionId));
    }

    @PatchMapping("/{institutionId}")
    public ResponseEntity<MessageResponseDto> updateInstitution(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UpdateInstitutionRequestDto requestDto,
            @PathVariable Long institutionId){
        return ResponseEntity.ok(institutionService.updateInstitution(userDetails, requestDto, institutionId));
    }



}
