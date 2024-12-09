package click.ahimmoyak.institutionservice.institution.dto;

import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import click.ahimmoyak.institutionservice.course.dto.EnrollmentDto;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record StartCourseProvideDetailResponseDto(
        String courseTitle,
        String companyName,
        LocalDate beginDate,
        LocalDate endDate,
        CourseProvideState state,
        List<EnrollmentDto> learnerList
) {
}
