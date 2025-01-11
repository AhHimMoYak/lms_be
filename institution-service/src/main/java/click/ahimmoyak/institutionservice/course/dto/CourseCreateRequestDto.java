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
        int period,
        CourseCategory category
) {
    public Course toEntity(Institution institution) {
        return Course.builder()
                .title(this.title)
                .introduction(this.introduction)
                .category(this.category)
                .period(this.period)
                .institution(institution)
                .state(CourseState.AVAILABLE)
                .instructor(this.instructor)
                .build();
    }
}
