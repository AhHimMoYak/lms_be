package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.EnrollmentState;
import click.ahimmoyak.institutionservice.course.entity.Enrollment;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EnrollmentInfoDto(
        Long enrollmentId,
        String username
) {
    public static EnrollmentInfoDto from(Enrollment enrollment) {
        return EnrollmentInfoDto.builder()
                .username(enrollment.getUser().getUsername())
                .build();
    }
}
