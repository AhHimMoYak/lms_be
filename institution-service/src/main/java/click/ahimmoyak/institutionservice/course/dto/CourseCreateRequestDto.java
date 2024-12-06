package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.CourseCategory;
import click.ahimmoyak.institutionservice.course.common.CourseState;
import click.ahimmoyak.institutionservice.course.entity.Course;
import click.ahimmoyak.institutionservice.institution.entity.Institution;
import lombok.Builder;

@Builder
public record CourseCreateRequestDto(
   String title,
   String introduction,
   String instructor,
   Institution institution,
   CourseCategory category
) {
    public Course toEntity() {
        return Course.builder()
                .title(this.title)
                .introduction(this.introduction)
                .category(this.category)
                .institution(this.institution)
                .state(CourseState.AVAILABLE)
                .instructor(this.instructor)
                .build();
    }
}
