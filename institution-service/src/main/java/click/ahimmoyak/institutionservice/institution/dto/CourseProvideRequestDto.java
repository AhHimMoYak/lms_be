package click.ahimmoyak.institutionservice.institution.dto;

import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import lombok.Builder;

@Builder
public record CourseProvideRequestDto(
        String action
) {



}
