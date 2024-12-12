package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.CourseCategory;
import click.ahimmoyak.institutionservice.course.common.CourseState;
import click.ahimmoyak.institutionservice.course.entity.Course;
import lombok.Builder;

@Builder
public record CourseListResponseDto(
   long id,
   String title,
   String introduction,
   String instructor,
   int period,
   CourseState state,
   CourseCategory category,
   String categoryTitle
) {
    public static CourseListResponseDto from(Course course) {
        return CourseListResponseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .instructor(course.getInstructor())
                .period(course.getPeriod())
                .state(course.getState())
                .category(course.getCategory())
                .categoryTitle(course.getCategory().getTitle())
                .build();
    }
}
