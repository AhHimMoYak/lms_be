package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import lombok.Builder;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CourseProvideDetailResponseDto(
        Long courseProvideId,
        String courseTitle,
        LocalDateTime createdDate,
        String instructor,
        String companyName,
        String institutionName,
        String email,
        int period,
        String phone,
        LocalDate beginDate,
        LocalDate endDate,
        long attendeeCount,
        CourseProvideState state,
        List<EnrollmentInfoDto> learnerList
) {
}
