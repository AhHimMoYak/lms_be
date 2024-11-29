package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.EnrollmentState;
import click.ahimmoyak.institutionservice.course.entity.Enrollment;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EnrollmentInfoDto(
        Long enrollmentId,
        String username,
        String name,
        String email,
        LocalDate birth,
        EnrollmentState state
) {
    public static EnrollmentInfoDto from(Enrollment enrollment) {
        return EnrollmentInfoDto.builder()
                .enrollmentId(enrollment.getId())
                .username(enrollment.getUser().getUsername())
                .name(enrollment.getUser().getName())
                .email(enrollment.getUser().getEmail())
                .birth(enrollment.getUser().getBirth())
                .build();
    }
}
