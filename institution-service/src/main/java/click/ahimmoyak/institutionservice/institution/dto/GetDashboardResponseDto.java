package click.ahimmoyak.institutionservice.institution.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GetDashboardResponseDto(
        Long totalCourse,
        Long totalCourseProvide,
        Long totalEnrollment,
        Long totalProgressCourseProvide,
        List<GetProgressCourseProvideListDto>  progressCourseProvideList
) {
}
