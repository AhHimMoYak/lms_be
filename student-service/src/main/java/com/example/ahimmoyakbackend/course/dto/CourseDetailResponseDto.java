package com.example.ahimmoyakbackend.course.dto;


import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.common.CourseState;
import com.example.ahimmoyakbackend.course.entity.Course;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CourseDetailResponseDto(
        String title,
        String introduction,
        String instructor,
        LocalDate beginDate,
        LocalDate endDate,
        CourseState state,
        CourseCategory category,
        Long institutionId,
        List<CurriculumListResponseDto> curriculumList
){
    public static CourseDetailResponseDto from(Course course, List<CurriculumListResponseDto> curriculumList) {
        return CourseDetailResponseDto.builder()
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .instructor(course.getInstructor())
                // Todo 코스디테일 반환시 CourseProvide 의 beginDate, endDate 함께 반환하도록 Dto 수정
//                .beginDate(course.getBeginDate())
//                .endDate(course.getEndDate())
                .state(course.getState())
                .category(course.getCategory())
                .curriculumList(curriculumList)
                .build();
    }
}
