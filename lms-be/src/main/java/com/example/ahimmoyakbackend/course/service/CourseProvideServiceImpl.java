package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.dto.CourseProvideDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseProvidesResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CourseProvideServiceImpl implements CourseProvideService {
    @Override
    public CourseProvideDetailResponseDto getCourseDetail(UserDetails userDetails) {
        return null;
    }

    @Override
    public CourseProvideDetailResponseDto getCourseDetailByInstitution(UserDetails userDetails) {
        return null;
    }

    @Override
    public CourseProvideDetailResponseDto getCourseDetailByEmployee(UserDetails userDetails) {
        return null;
    }

    @Override
    public CourseProvidesResponseDto getCourseListByInstitution(UserDetails userDetails) {
        return null;
    }

    @Override
    public CourseProvidesResponseDto getCourseListByEmployee(UserDetails userDetails) {
        return null;
    }
}
