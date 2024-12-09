package click.ahimmoyak.institutionservice.institution.service;

import click.ahimmoyak.institutionservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.institutionservice.course.dto.CourseProvideDetailResponseDto;
import click.ahimmoyak.institutionservice.course.dto.CourseProvidesResponseDto;
import click.ahimmoyak.institutionservice.course.dto.EnrollmentRequestDto;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import click.ahimmoyak.institutionservice.institution.dto.*;
import org.springframework.security.core.userdetails.UserDetails;

public interface InstitutionService {
    MessageResponseDto createInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto);

    MessageResponseDto updateInstitution(Long userId, UpdateInstitutionRequestDto requestDto);

    GetInstitutionDetailRequestDto getInstitutionDetail(Long userId);

    GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails, Long institutionId);

    CourseProvidesResponseDto getCourseProvideListByInstitution(Long userId);

    MessageResponseDto courseProvideResponse(Long userId, Long courseProvideId, CourseProvideRequestDto requestDto);

    CourseProvideDetailResponseDto getCourseProvideDetailByInstitution(Long userId, Long courseProvideId);

    MessageResponseDto confirmEnrollments(Long userId, Long courseProvideId, EnrollmentRequestDto requestDto);

    StartCourseProvideDetailResponseDto getStartCourseProvideDetailByInstitution(Long userId, Long courseProvideId);

    GetDashboardResponseDto getDashboard(Long userId);
}
