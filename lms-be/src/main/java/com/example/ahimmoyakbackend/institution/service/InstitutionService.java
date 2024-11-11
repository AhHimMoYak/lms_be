package com.example.ahimmoyakbackend.institution.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.institution.dto.UpdateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.UserInstitutionIdResponseDto;
import com.example.ahimmoyakbackend.institution.dto.GetInstitutionDetailRequestDto;

public interface InstitutionService {
    MessageResponseDto createInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto);
    MessageResponseDto updateInstitution(UserDetailsImpl userDetails, UpdateInstitutionRequestDto requestDto, Long institutionId);
    GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails, Long institutionId);
    UserInstitutionIdResponseDto getInstitutionId(UserDetailsImpl userDetails);
}
