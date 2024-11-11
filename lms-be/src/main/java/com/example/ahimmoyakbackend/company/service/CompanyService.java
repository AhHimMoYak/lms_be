package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;

import java.util.List;

public interface CompanyService {
    MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto createCompanyRequestDto);

    List<SearchCompanyResponseDto> searchCompany(String name);

    MessageResponseDto updateCompany(UserDetailsImpl userDetails, Long companyId, UpdateCompanyRequestDto requestDto);

    CheckCompanyResponseDto checkCompanyEmail(String companyEmail, String userEmail);

    MessageResponseDto addAffiliation(UserDetailsImpl userDetails, Long companyId);

    MessageResponseDto disconnectCompany(UserDetailsImpl userDetails);

    MessageResponseDto deleteAffiliation(UserDetailsImpl userDetails, Long userId);

    List<GetEmployeeListResponseDto> getEmployeeList(UserDetailsImpl userDetails);

    MessageResponseDto createCourseProvider(UserDetailsImpl userDetails);

    List<CourseProvideListDto> getCourseProvideList(UserDetailsImpl userDetails);

    MessageResponseDto submitEmployeeListForEnrollment (UserDetailsImpl userDetails, submitEmployeeListRequestDto requestDto);
}
