package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.CourseCategory;
import click.ahimmoyak.institutionservice.course.common.CourseState;
import click.ahimmoyak.institutionservice.course.entity.Course;
import click.ahimmoyak.institutionservice.institution.entity.Institution;
import lombok.Builder;

@Builder
public record CourseUpdateRequestDto(
        String title,
        String introduction,
        String instructor,
        int period
        //CourseCategory category
) {
    public Course toEntity() {
        return Course.builder()
                .title(this.title)
                .introduction(this.introduction)
                .category(CourseCategory.TEXTILE_CLOTHING)
                .period(this.period)
                .instructor(this.instructor)
                .build();
    }
}
