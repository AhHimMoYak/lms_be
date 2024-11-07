package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.dto.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CourseProvideServiceImpl implements CourseProvideService {
    @Override
    public CourseProvideDetailByCompanyResponseDto getCourseDetailByCompany(UserDetails userDetails) {
        return null;
    }

    @Override
    public CourseProvideDetailByInstitutionResponseDto getCourseDetailByInstitution(UserDetails userDetails) {
        return null;
    }

    @Override
    public CourseProvideDetailByEmployeeResponseDto getCourseDetailByEmployee(UserDetails userDetails) {
        return null;
    }

    @Override
    public CourseProvideGetCourseListByInstitutionResponseDto getCourseListByInstitution(UserDetails userDetails) {
        return null;
    }

    @Override
    public CourseProvideGetCourseListByEmployeeResponseDto getCourseListByEmployee(UserDetails userDetails) {
        return null;
    }
}
