package com.example.ahimmoyakbackend.institution.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionResponseDto;
import com.example.ahimmoyakbackend.institution.dto.UserInstitutionIdResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {
    @Override
    public boolean createInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto) {
        return false;
    }

    @Override
    public boolean updateInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto) {
        return false;
    }

    @Override
    public CreateInstitutionResponseDto getInstitutionDetail(UserDetailsImpl userDetails) {
        return null;
    }

    @Override
    public UserInstitutionIdResponseDto getInstitutionId(UserDetailsImpl userDetails) {
        return null;
    }
}
