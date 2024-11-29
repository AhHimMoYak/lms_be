package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.company.entity.Company;
import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import click.ahimmoyak.institutionservice.course.entity.Course;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import click.ahimmoyak.institutionservice.institution.entity.Institution;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseProvideDto(
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
    public static CourseProvideDto from(CourseProvide courseProvide, Course course, Company company, Institution institution) {
        return CourseProvideDto.builder()
                .courseProvideId(courseProvide.getId())
                .courseTitle(course.getTitle())
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
