package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.company.dto.CreateCompanyRequestDto;
import com.example.ahimmoyakbackend.company.dto.SearchCompanyResponseDto;
import com.example.ahimmoyakbackend.company.dto.SearchCompanyRequestDto;
import com.example.ahimmoyakbackend.company.dto.UpdateCompanyRequestDto;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    @Override
    @Transactional
    public MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto requestDto) {
        return null;
    }

    @Override
    public SearchCompanyResponseDto searchCompany(UserDetailsImpl userDetails, SearchCompanyRequestDto company) {
        return SearchCompanyResponseDto.builder().build();
    }

    @Override
    public MessageResponseDto updateCompany(UserDetailsImpl userDetails, Long companyId, UpdateCompanyRequestDto requestDto) {
        return null;
    }

    @Override
    public MessageResponseDto deleteCompany(UserDetailsImpl userDetails, Long companyId) {
        return null;
    }


}
