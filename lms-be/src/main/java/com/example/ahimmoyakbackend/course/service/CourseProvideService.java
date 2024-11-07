package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.dto.CourseProvideDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseProvidesResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface CourseProvideService {
    CourseProvideDetailResponseDto getCourseDetailByCompany(UserDetails userDetails);
    CourseProvideDetailResponseDto getCourseDetailByInstitution(UserDetails userDetails);
    CourseProvideDetailResponseDto getCourseDetailByEmployee(UserDetails userDetails);
    CourseProvidesResponseDto getCourseListByInstitution(UserDetails userDetails);
    CourseProvidesResponseDto getCourseListByEmployee(UserDetails userDetails);
}
