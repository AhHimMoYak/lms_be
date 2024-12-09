package click.ahimmoyak.companyservice.company.service;

import click.ahimmoyak.companyservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.companyservice.company.dto.*;
import click.ahimmoyak.companyservice.global.dto.MessageResponseDto;

import java.util.List;

public interface CompanyService {

    CompanyDetailResponseDto getCompany(UserDetailsImpl userDetails);

    MessageResponseDto updateCompany(UserDetailsImpl userDetails, String name, UpdateCompanyRequestDto requestDto);

    MessageResponseDto disconnectCompany(UserDetailsImpl userDetails);

    MessageResponseDto deleteAffiliation(UserDetailsImpl userDetails, String username);

//    List<GetEmployeeListResponseDto> getEmployeeList(UserDetailsImpl userDetails);

    MessageResponseDto createCourseProvider(UserDetailsImpl userDetails, Long courseId, CreateCourseProvideRequestDto requestDto);

    List<CourseProvideListResponseDto> getCourseProvideList(UserDetailsImpl userDetails);

    MessageResponseDto submitEmployeeListForEnrollment (UserDetailsImpl userDetails, submitEmployeeListRequestDto requestDto);
}
