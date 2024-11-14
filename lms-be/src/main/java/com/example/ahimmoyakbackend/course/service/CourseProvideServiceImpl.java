package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.dto.CourseProvideDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseProvidesResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CourseProvideServiceImpl implements CourseProvideService {

    @Override
    public CourseProvideDetailResponseDto getCourseProvideDetailByInstitution(UserDetails userDetails) {
        return null;
    }

    @Override
    public CourseProvideDetailResponseDto getCourseProvideDetailByEmployee(UserDetails userDetails) {
        return null;
    }


    @Override
    public CourseProvidesResponseDto getCourseProvideListByEmployee(UserDetails userDetails) {
        return null;
    }
}
