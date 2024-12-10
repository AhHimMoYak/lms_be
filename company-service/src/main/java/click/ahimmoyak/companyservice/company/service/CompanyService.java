package click.ahimmoyak.companyservice.company.service;

import click.ahimmoyak.companyservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.companyservice.company.dto.*;
import click.ahimmoyak.companyservice.global.dto.MessageResponseDto;

import java.util.List;

public interface CompanyService {

    CompanyDetailResponseDto getCompany(Long userId);

    MessageResponseDto updateCompany( Long companyId, UpdateCompanyRequestDto requestDto);

    MessageResponseDto disconnectCompany(UserDetailsImpl userDetails);

    MessageResponseDto deleteAffiliation(UserDetailsImpl userDetails, String username);

//    List<GetEmployeeListResponseDto> getEmployeeList(UserDetailsImpl userDetails);

    MessageResponseDto createCourseProvider(Long userId, Long courseId, CreateCourseProvideRequestDto requestDto);

    List<CourseProvideListResponseDto> getCourseProvideList(Long userId);

    MessageResponseDto submitEmployeeListForEnrollment (Long userId, submitEmployeeListRequestDto requestDto);
}
