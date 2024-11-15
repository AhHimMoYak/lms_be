package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;

import java.util.List;

public interface CompanyService {
    MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto createCompanyRequestDto);

    List<SearchCompanyResponseDto> searchCompany(String name);

    CompanyDetailResponseDto getCompany(UserDetailsImpl userDetails);

    MessageResponseDto updateCompany(UserDetailsImpl userDetails, String name, UpdateCompanyRequestDto requestDto);

    CheckCompanyResponseDto checkCompanyEmail(String companyEmail, String userEmail);

    MessageResponseDto addAffiliation(UserDetailsImpl userDetails, Long companyId);

    MessageResponseDto disconnectCompany(UserDetailsImpl userDetails);

    MessageResponseDto deleteAffiliation(UserDetailsImpl userDetails, String username);

    List<GetEmployeeListResponseDto> getEmployeeList(UserDetailsImpl userDetails);

    MessageResponseDto createCourseProvider(UserDetailsImpl userDetails, Long courseId, CreateCourseProvideRequestDto requestDto);

    List<CourseProvideListResponseDto> getCourseProvideList(UserDetailsImpl userDetails);

    MessageResponseDto submitEmployeeListForEnrollment (UserDetailsImpl userDetails, submitEmployeeListRequestDto requestDto);
}
