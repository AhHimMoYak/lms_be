package click.ahimmoyak.companyservice.course.dto;


import click.ahimmoyak.companyservice.course.common.CourseCategory;
import click.ahimmoyak.companyservice.course.common.CourseState;
import click.ahimmoyak.companyservice.course.entity.Course;
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
        String institutionName,
        List<CurriculumListResponseDto> curriculumList,
        List<CourseProvideListDto> courseProvides
){
    public static CourseDetailResponseDto from(Course course, List<CurriculumListResponseDto> curriculumList, List<CourseProvideListDto> courseProvides) {
        return CourseDetailResponseDto.builder()
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .instructor(course.getInstructor())
                .institutionId(course.getInstitution().getId())
                .institutionName(course.getInstitution().getName())
                .beginDate(courseProvides.getFirst().beginDate())
                .endDate(courseProvides.getLast().endDate())
                .period(course.getPeriod())
                .state(course.getState())
                .category(course.getCategory())
                .categoryTitle(course.getCategory().getTitle())
                .curriculumList(curriculumList)
                .courseProvides(courseProvides)
                .build();
    }
}
