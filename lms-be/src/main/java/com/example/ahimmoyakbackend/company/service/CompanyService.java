package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.company.dto.CreateCompanyRequestDto;
import com.example.ahimmoyakbackend.company.dto.SearchCompanyResponseDto;
import com.example.ahimmoyakbackend.company.dto.SearchCompanyRequestDto;
import com.example.ahimmoyakbackend.company.dto.UpdateCompanyRequestDto;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;

public interface CompanyService {
    MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto createCompanyRequestDto);
    SearchCompanyResponseDto searchCompany(UserDetailsImpl userDetails, SearchCompanyRequestDto company);
    MessageResponseDto updateCompany(UserDetailsImpl userDetails, Long companyId, UpdateCompanyRequestDto requestDto);
    MessageResponseDto deleteCompany(UserDetailsImpl userDetails, Long companyId);
}
