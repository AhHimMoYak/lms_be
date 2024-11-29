package click.ahimmoyak.studentservice.company.dto;

import click.ahimmoyak.studentservice.company.entity.Company;
import click.ahimmoyak.studentservice.course.common.CourseProvideState;
import click.ahimmoyak.studentservice.course.entity.CourseProvide;
import click.ahimmoyak.studentservice.institution.entity.Institution;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseProvideListResponseDto(
        Long courseProvideId,
        String courseTitle,
        String companyName,
        String institutionName,
        LocalDate beginDate,
        LocalDate endDate,
        CourseProvideState state,
        long attendeeCount,
        long deposit
) {
    public static CourseProvideListResponseDto from(CourseProvide courseProvide, Company company, Institution institution) {
        return CourseProvideListResponseDto.builder()
                .courseProvideId(courseProvide.getId())
                .courseTitle(courseProvide.getCourse().getTitle())
                .companyName(company.getName())
                .institutionName(institution.getName())
                .beginDate(courseProvide.getBeginDate())
                .endDate(courseProvide.getEndDate())
                .state(courseProvide.getState())
                .attendeeCount(courseProvide.getAttendeeCount())
                .deposit(courseProvide.getDeposit())
                .build();
    }
}
