package click.ahimmoyak.institutionservice.institution.dto;

import lombok.Builder;

@Builder
public record GetProgressCourseProvideListDto(
        String courseTitle,
        int period,
        Long companyProvided
) {
}
