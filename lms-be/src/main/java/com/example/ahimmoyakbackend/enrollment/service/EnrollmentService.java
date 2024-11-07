package com.example.ahimmoyakbackend.enrollment.service;

import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentConfirmRequestDto;
import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentIdResponseDto;
import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentSubmitEmployeeListRequestDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface EnrollmentService {
    boolean make(UserDetails userDetails, long courseId);
    boolean cancel(UserDetails userDetails, long enrollId);
    boolean cancel(long courseId, UserDetails userDetails);
    EnrollmentIdResponseDto getEnrollId(UserDetails userDetails, Long courseId);
    String submitEmployeeListForEnrollment(UserDetails userDetails, EnrollmentSubmitEmployeeListRequestDto requestDto);
    String confirmEnrollments(UserDetails userDetails, EnrollmentConfirmRequestDto requestDto);
}
