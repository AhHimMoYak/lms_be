package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.company.dto.CreateCompanyRequestDto;
import com.example.ahimmoyakbackend.company.dto.SearchCompanyRequestDto;
import com.example.ahimmoyakbackend.company.dto.UpdateCompanyRequestDto;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;

public interface CompanyService {
    public MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto createCompanyRequestDto);
    public MessageResponseDto searchCompany(UserDetailsImpl userDetails, SearchCompanyRequestDto company);
    public MessageResponseDto updateCompany(UserDetailsImpl userDetails, Long companyId, UpdateCompanyRequestDto requestDto);
    public MessageResponseDto deleteCompany(UserDetailsImpl userDetails, Long companyId);
}
