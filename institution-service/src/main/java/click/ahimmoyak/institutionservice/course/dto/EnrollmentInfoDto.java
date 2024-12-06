package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.EnrollmentState;
import click.ahimmoyak.institutionservice.course.entity.Enrollment;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EnrollmentInfoDto(
        String username,
        EnrollmentState state
) {
    public static EnrollmentInfoDto from(Enrollment enrollment) {
        return EnrollmentInfoDto.builder()
                .username(enrollment.getUser().getUsername())
                .state(enrollment.getState())
                .build();
    }
}
