package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CourseProvideDetailResponseDto(
        LocalDate beginDate,
        LocalDate endDate,
        long attendeeCount,
        CourseProvideState state,
        List<EnrollmentInfoDto> learnerList
) {
}
