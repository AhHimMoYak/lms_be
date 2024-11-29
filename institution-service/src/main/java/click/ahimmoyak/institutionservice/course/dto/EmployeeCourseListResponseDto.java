package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.CourseCategory;
import click.ahimmoyak.institutionservice.course.common.CourseState;
import click.ahimmoyak.institutionservice.course.entity.Course;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EmployeeCourseListResponseDto(
        long id,
        String title,
        String institution,
        String instructor,
        CourseCategory category,
        LocalDate beginDate,
        LocalDate endDate,
        CourseState state
) {
    public static EmployeeCourseListResponseDto from(Course course, CourseProvide courseProvide) {
        return EmployeeCourseListResponseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .institution(course.getInstitution().getName())
                .instructor(course.getInstructor())
                .category(course.getCategory())
                .beginDate(courseProvide.getBeginDate())
                .endDate(courseProvide.getEndDate())
                .state(course.getState())
                .build();
    }
}