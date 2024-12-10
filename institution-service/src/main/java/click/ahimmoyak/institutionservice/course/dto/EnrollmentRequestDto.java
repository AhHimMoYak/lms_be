package click.ahimmoyak.institutionservice.course.dto;

import lombok.Builder;

@Builder
public record EnrollmentRequestDto(
        String action
) {
}
