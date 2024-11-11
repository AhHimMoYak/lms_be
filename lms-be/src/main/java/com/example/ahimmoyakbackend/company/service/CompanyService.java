package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.FormerCompanyInfoRequestDto;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;

public interface CompanyService {
    MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto createCompanyRequestDto);
    SearchCompanyResponseDto searchCompany(UserDetailsImpl userDetails, String name);
    MessageResponseDto updateCompany(UserDetailsImpl userDetails, Long companyId, UpdateCompanyRequestDto requestDto);
    MessageResponseDto deleteCompany(UserDetailsImpl userDetails, Long companyId);
    MessageResponseDto checkCompanyEmail(String email);
    MessageResponseDto addAffiliation(AddAffiliationRequestDto requestDto);
    GetEmployeeListResponseDto getEmployeeList(UserDetailsImpl userDetails);
    MessageResponseDto disconnectCompany(Long companyId, UserDetailsImpl userDetails);
}
