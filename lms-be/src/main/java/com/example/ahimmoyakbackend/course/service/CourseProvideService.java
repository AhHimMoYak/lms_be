package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.dto.*;
import org.springframework.security.core.userdetails.UserDetails;

public interface CourseProvideService {
    CourseProvideDetailByCompanyResponseDto getCourseDetailByCompany(UserDetails userDetails);
    CourseProvideDetailByInstitutionResponseDto getCourseDetailByInstitution(UserDetails userDetails);
    CourseProvideDetailByEmployeeResponseDto getCourseDetailByEmployee(UserDetails userDetails);
    CourseProvideGetCourseListByInstitutionResponseDto getCourseListByInstitution(UserDetails userDetails);
    CourseProvideGetCourseListByEmployeeResponseDto getCourseListByEmployee(UserDetails userDetails);
}
