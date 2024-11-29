package click.ahimmoyak.studentservice.course.dto;

import click.ahimmoyak.studentservice.course.common.CourseProvideState;
import click.ahimmoyak.studentservice.course.entity.CourseProvide;
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
    public static CourseProvideDetailResponseDto from(CourseProvide courseProvide) {
        return CourseProvideDetailResponseDto.builder()
                .beginDate(courseProvide.getBeginDate())
                .endDate(courseProvide.getEndDate())
                .attendeeCount(courseProvide.getAttendeeCount())
                .state(courseProvide.getState())
                .learnerList(
                        courseProvide.getEnrollments().stream()
                                .map(EnrollmentInfoDto::from)
                                .toList()
                )
                .build();
    }
}
