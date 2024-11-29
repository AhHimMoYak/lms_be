package com.example.ahimmoyakbackend.institution.dto;

import com.example.ahimmoyakbackend.course.common.CourseProvideState;
import lombok.Builder;

@Builder
public record CourseProvideRequestDto(
        CourseProvideState state
) {
}
