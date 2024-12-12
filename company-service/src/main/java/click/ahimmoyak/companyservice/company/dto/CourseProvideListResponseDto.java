package click.ahimmoyak.companyservice.company.dto;

import click.ahimmoyak.companyservice.company.entity.Company;
import click.ahimmoyak.companyservice.course.common.CourseProvideState;
import click.ahimmoyak.companyservice.course.entity.CourseProvide;
import click.ahimmoyak.companyservice.institution.entity.Institution;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseProvideListResponseDto(
        Long courseId,
        Long courseProvideId,
        String title,
        String companyName,
        String institutionName,
        LocalDate creationDate,
        LocalDate beginDate,
        LocalDate endDate,
        CourseProvideState state,
        String instructor,
        String category,
        long attendeeCount,
        long deposit
) {
    public static CourseProvideListResponseDto from(CourseProvide courseProvide, Company company, Institution institution) {
        return CourseProvideListResponseDto.builder()
                .courseId(courseProvide.getCourse().getId())
                .courseProvideId(courseProvide.getId())
                .title(courseProvide.getCourse().getTitle())
                .companyName(company.getName())
                .institutionName(institution.getName())
                .creationDate(courseProvide.getCreatedAt().toLocalDate())
                .beginDate(courseProvide.getBeginDate())
                .endDate(courseProvide.getEndDate())
                .state(courseProvide.getState())
                .attendeeCount(courseProvide.getAttendeeCount())
                .deposit(courseProvide.getDeposit())
                .instructor(courseProvide.getCourse().getInstructor())
                .category(courseProvide.getCourse().getCategory().getTitle())
                .build();
    }
}
