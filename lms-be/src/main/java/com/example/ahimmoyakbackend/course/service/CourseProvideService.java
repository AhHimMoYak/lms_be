package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.dto.CourseProvideDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseProvidesResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface CourseProvideService {
    CourseProvideDetailResponseDto getCourseProvideDetailByInstitution(UserDetails userDetails);

    CourseProvideDetailResponseDto getCourseProvideDetailByEmployee(UserDetails userDetails);

    CourseProvidesResponseDto getCourseProvideListByEmployee(UserDetails userDetails);
}
