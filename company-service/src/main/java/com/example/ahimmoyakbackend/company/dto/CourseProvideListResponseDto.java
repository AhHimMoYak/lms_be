package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.course.common.CourseProvideState;
import com.example.ahimmoyakbackend.course.entity.CourseProvide;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseProvideListResponseDto(
        Long courseId,
        Long courseProvideId,
        String title,
        String companyName,
        String institutionName,
        LocalDate beginDate,
        LocalDate endDate,
        CourseProvideState state,
        String instructor,
        long attendeeCount,
        long deposit
) {
    public static CourseProvideListResponseDto from(CourseProvide courseProvide, Company company, Institution institution) {
        return CourseProvideListResponseDto.builder()
                .courseId(courseProvide.getCourse().getId())
                .courseProvideId(courseProvide.getId())
                .title(courseProvide.getCourse().getTitle())
                .companyName(company.getName())
                .institutionName(institution.getName())
                .beginDate(courseProvide.getBeginDate())
                .endDate(courseProvide.getEndDate())
                .state(courseProvide.getState())
                .attendeeCount(courseProvide.getAttendeeCount())
                .deposit(courseProvide.getDeposit())
                .instructor(courseProvide.getCourse().getInstructor())
                .build();
    }
}
