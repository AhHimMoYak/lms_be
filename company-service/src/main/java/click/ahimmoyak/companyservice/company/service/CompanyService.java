package click.ahimmoyak.companyservice.company.service;

import click.ahimmoyak.companyservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.companyservice.company.dto.*;
import click.ahimmoyak.companyservice.global.dto.MessageResponseDto;

import java.util.List;

public interface CompanyService {
    MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto createCompanyRequestDto);

    List<SearchCompanyResponseDto> searchCompany(String name);

    CompanyDetailResponseDto getCompany(UserDetailsImpl userDetails);

    MessageResponseDto updateCompany(UserDetailsImpl userDetails, String name, UpdateCompanyRequestDto requestDto);

    CheckCompanyResponseDto checkCompanyEmail(String companyEmail, String userEmail);

    MessageResponseDto addAffiliation(UserDetailsImpl userDetails, String companyName);

    MessageResponseDto disconnectCompany(UserDetailsImpl userDetails);

    MessageResponseDto deleteAffiliation(UserDetailsImpl userDetails, String username);

    List<GetEmployeeListResponseDto> getEmployeeList(UserDetailsImpl userDetails);

    MessageResponseDto createCourseProvider(UserDetailsImpl userDetails, Long courseId, CreateCourseProvideRequestDto requestDto);

    List<CourseProvideListResponseDto> getCourseProvideList(UserDetailsImpl userDetails);

    MessageResponseDto submitEmployeeListForEnrollment (UserDetailsImpl userDetails, submitEmployeeListRequestDto requestDto);
}
