package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.course.common.CourseProvideState;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.CourseProvide;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseProvideDetailDto(
        Long courseProvideId,
        String courseTitle,
        String companyName,
        String institutionName,
        LocalDate beginDate,
        LocalDate endDate,
        CourseProvideState state,
        long attendeeCount,
        long deposit
) {
    public static CourseProvideDetailDto from(CourseProvide courseProvide, Course course, Company company, Institution institution) {
        return CourseProvideDetailDto.builder()
                .courseProvideId(courseProvide.getId())
                .courseTitle(course.getTitle())
                .companyName(company.getName())
                .institutionName(institution.getName())
                .beginDate(courseProvide.getBeginDate())
                .endDate(courseProvide.getEndDate())
                .state(courseProvide.getState())
                .attendeeCount(courseProvide.getAttendeeCount())
                .deposit(courseProvide.getDeposit())
                .build();
    }
}
