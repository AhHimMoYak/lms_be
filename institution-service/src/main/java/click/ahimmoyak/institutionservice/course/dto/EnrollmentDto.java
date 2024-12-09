package click.ahimmoyak.institutionservice.course.dto;

import lombok.Builder;

@Builder
public record EnrollmentDto(
        Long enrollmentId,
        String enrollmentName,
        Long progress) {
}
