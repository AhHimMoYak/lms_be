package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;

import java.util.List;

public interface CompanyService {
    MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto createCompanyRequestDto);

    List<SearchCompanyResponseDto> searchCompany(String name);

    CompanyDetailResponseDto getCompany(UserDetailsImpl userDetails);

    CheckCompanyResponseDto checkCompanyEmail(String companyEmail, String userEmail);

    MessageResponseDto addAffiliation(UserDetailsImpl userDetails, String companyName);

    MessageResponseDto disconnectCompany(UserDetailsImpl userDetails);

    MessageResponseDto deleteAffiliation(UserDetailsImpl userDetails, String username);


}
