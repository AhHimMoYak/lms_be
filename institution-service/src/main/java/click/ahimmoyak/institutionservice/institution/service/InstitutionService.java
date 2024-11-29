package click.ahimmoyak.institutionservice.institution.service;

import click.ahimmoyak.institutionservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.institutionservice.course.dto.CourseProvideDetailResponseDto;
import click.ahimmoyak.institutionservice.course.dto.CourseProvidesResponseDto;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import click.ahimmoyak.institutionservice.institution.dto.CourseProvideRequestDto;
import click.ahimmoyak.institutionservice.institution.dto.CreateInstitutionRequestDto;
import click.ahimmoyak.institutionservice.institution.dto.GetInstitutionDetailRequestDto;
import click.ahimmoyak.institutionservice.institution.dto.UpdateInstitutionRequestDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface InstitutionService {
    MessageResponseDto createInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto);

    MessageResponseDto updateInstitution(UserDetailsImpl userDetails, UpdateInstitutionRequestDto requestDto, Long institutionId);

    GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails);

    GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails, Long institutionId);

    CourseProvidesResponseDto getCourseProvideListByInstitution(UserDetailsImpl userDetails);

    MessageResponseDto courseProvideResponse(UserDetailsImpl userDetails, Long courseProvideId, CourseProvideRequestDto requestDto);

    CourseProvideDetailResponseDto getCourseProvideDetailByInstitution(UserDetails userDetails, Long courseProvideId);

    MessageResponseDto confirmEnrollments(UserDetails userDetails, Long courseProvideId);
}
