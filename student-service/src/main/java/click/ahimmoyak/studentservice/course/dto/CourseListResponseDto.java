package click.ahimmoyak.studentservice.course.dto;

import click.ahimmoyak.studentservice.course.common.CourseCategory;
import click.ahimmoyak.studentservice.course.common.CourseProvideState;
import click.ahimmoyak.studentservice.course.common.CourseState;
import click.ahimmoyak.studentservice.course.entity.Course;
import lombok.Builder;

@Builder
public record CourseListResponseDto(
   long id,
   String title,
   String introduction,
   String instructor,
   long totalContentCount,
   long completedContentCount,
   CourseProvideState state,
   CourseCategory category
) {
    public static CourseListResponseDto from(Course course) {
        return CourseListResponseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .instructor(course.getIntroduction())
                .category(course.getCategory())
                .build();
    }
}
