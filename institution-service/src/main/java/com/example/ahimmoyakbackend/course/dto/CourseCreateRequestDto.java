package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.common.CourseState;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseCreateRequestDto(
   String title,
   String introduction,
   String instructor,
   Institution institution,
   CourseCategory category
) {
    // Todo 코스 생성시 교육기관 정보가 들어가도록 수정해야함
    public Course toEntity(User user, CourseCreateRequestDto requestDto) {
        return Course.builder()
                .title(requestDto.title)
                .introduction(requestDto.introduction)
                .category(requestDto.category)
                .institution(user.getManager().getInstitution())
                .state(CourseState.AVAILABLE)
                .instructor(requestDto.instructor)
                .build();
    }
}
