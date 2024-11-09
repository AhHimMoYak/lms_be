package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;

import java.util.List;

public interface CompanyService {
    MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto createCompanyRequestDto);
    List<SearchCompanyResponseDto> searchCompany(String name);
    MessageResponseDto updateCompany(UserDetailsImpl userDetails, Long companyId, UpdateCompanyRequestDto requestDto);
    MessageResponseDto deleteCompany(UserDetailsImpl userDetails, Long companyId);
    MessageResponseDto checkCompanyEmail(CheckCompanyEmailRequestDto requestDto);
    MessageResponseDto addAffiliation(AddAffiliationRequestDto requestDto);
    GetEmployeeListResponseDto getEmployeeList(UserDetailsImpl userDetails);
}
