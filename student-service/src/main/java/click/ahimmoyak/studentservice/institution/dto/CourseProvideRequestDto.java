package click.ahimmoyak.studentservice.institution.dto;

import click.ahimmoyak.studentservice.course.common.CourseProvideState;
import lombok.Builder;

@Builder
public record CourseProvideRequestDto(
        CourseProvideState state
) {
}
