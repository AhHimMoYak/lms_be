package click.ahimmoyak.institutionservice.course.dto;


import click.ahimmoyak.institutionservice.course.common.CourseCategory;
import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import click.ahimmoyak.institutionservice.course.common.CourseState;
import click.ahimmoyak.institutionservice.course.entity.Course;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CourseDetailResponseDto(
        String title,
        String introduction,
        String instructor,
        int period,
        LocalDate beginDate,
        LocalDate endDate,
        CourseState state,
        CourseCategory category,
        String categoryTitle,
        Long institutionId,
        List<CurriculumListResponseDto> curriculumList,
        List<CourseProvideListDto> courseProvides
){
    public static CourseDetailResponseDto from(Course course, List<CurriculumListResponseDto> curriculumList, List<CourseProvideListDto> courseProvides) {
        return CourseDetailResponseDto.builder()
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .instructor(course.getInstructor())
                .institutionId(course.getInstitution().getId())
                .period(course.getPeriod())
                .state(course.getState())
                .category(course.getCategory())
                .categoryTitle(course.getCategory().getTitle())
                .curriculumList(curriculumList)
                .courseProvides(courseProvides)
                .build();
    }

}
