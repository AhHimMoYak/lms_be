package com.example.ahimmoyakbackend.institution.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.course.dto.CourseProvideDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseProvidesResponseDto;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.institution.dto.*;
import org.springframework.security.core.userdetails.UserDetails;

public interface InstitutionService {
    MessageResponseDto createInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto);

    MessageResponseDto updateInstitution(UserDetailsImpl userDetails, UpdateInstitutionRequestDto requestDto, Long institutionId);

    GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails);

    GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails, Long institutionId);

    CourseProvidesResponseDto getCourseProvideListByInstitution(UserDetailsImpl userDetails);

    MessageResponseDto courseProvideResponse(UserDetailsImpl userDetails, Long courseProvideId, CourseProvideRequestDto requestDto);

    CourseProvideDetailResponseDto getCourseProvideDetailByInstitution(UserDetails userDetails, Long courseProvideId);

    MessageResponseDto confirmEnrollments(UserDetails userDetails, ConfirmEnrollmentsRequestDto requestDto, Long courseProvideId);
}
