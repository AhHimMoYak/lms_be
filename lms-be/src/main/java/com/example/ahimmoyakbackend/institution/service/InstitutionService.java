package com.example.ahimmoyakbackend.institution.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionResponseDto;
import com.example.ahimmoyakbackend.institution.dto.UserInstitutionIdResponseDto;

public interface InstitutionService {
    boolean createInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto);
    boolean updateInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto);
    CreateInstitutionResponseDto getInstitutionDetail(UserDetailsImpl userDetails);
    UserInstitutionIdResponseDto getInstitutionId(UserDetailsImpl userDetails);
}
