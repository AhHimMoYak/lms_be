package com.example.ahimmoyakbackend.course.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CourseProvidesResponseDto(
        List<CourseDetailResponseDto> courseDetailResponseDtoList
) {
    public static CourseProvidesResponseDto from(List<CourseDetailResponseDto> courseDetailResponseDtoList) {
        return CourseProvidesResponseDto.builder()
                .courseDetailResponseDtoList(courseDetailResponseDtoList)
                .build();
    }
}
