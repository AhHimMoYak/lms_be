package com.example.ahimmoyakbackend.institution.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionResponseDto;
import com.example.ahimmoyakbackend.institution.dto.UserInstitutionIdResponseDto;

public interface InstitutionService {
    MessageResponseDto createInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto);
    MessageResponseDto updateInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto);
    CreateInstitutionResponseDto getInstitutionDetail(UserDetailsImpl userDetails);
    UserInstitutionIdResponseDto getInstitutionId(UserDetailsImpl userDetails);
}
