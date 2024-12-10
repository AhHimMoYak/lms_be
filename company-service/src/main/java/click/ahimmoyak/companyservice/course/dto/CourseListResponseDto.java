package click.ahimmoyak.companyservice.course.dto;

import click.ahimmoyak.companyservice.course.common.CourseCategory;
import click.ahimmoyak.companyservice.course.common.CourseState;
import click.ahimmoyak.companyservice.course.entity.Course;
import lombok.Builder;

@Builder
public record CourseListResponseDto(
   long id,
   String title,
   String institution,
   String introduction,
   String instructor,
   CourseState state,
   CourseCategory category,
   String categoryTitle,
   int period
) {
    public static CourseListResponseDto from(Course course) {
        return CourseListResponseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .institution(course.getInstitution().getName())
                .introduction(course.getIntroduction())
                .instructor(course.getInstructor())
//                .beginDate(course.getBeginDate())
//                .endDate(course.getEndDate())
                .categoryTitle(course.getCategory().getTitle())
                .state(course.getState())
                .category(course.getCategory())
                .period(course.getPeriod())
                .build();
    }
}
