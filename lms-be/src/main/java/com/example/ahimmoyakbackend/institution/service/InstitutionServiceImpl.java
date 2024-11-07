package com.example.ahimmoyakbackend.institution.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionResponseDto;
import com.example.ahimmoyakbackend.institution.dto.UserInstitutionIdResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {
    @Override
    public MessageResponseDto createInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto) {
        return MessageResponseDto.builder().message("회사 생성 성공").build();
    }

    @Override
    public MessageResponseDto updateInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto) {
        return MessageResponseDto.builder().message("회사 수정 성공").build();
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
