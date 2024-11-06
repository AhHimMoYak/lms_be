package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.common.CourseState;
import com.example.ahimmoyakbackend.course.entity.Course;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseCreateRequestDto(
   String title,
   String introduction,
   String instructor,
   CourseCategory category
) {
    // Todo 코스 생성시 교육기관 정보가 들어가도록 수정해야함
    public Course toEntity() {
        return Course.builder()
                .title(this.title)
                .introduction(this.introduction)
                .category(this.category)
                .state(CourseState.AVAILABLE)
                .instructor(this.instructor)
                .build();
    }
}
