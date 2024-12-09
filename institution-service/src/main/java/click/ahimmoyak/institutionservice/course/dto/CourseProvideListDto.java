package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseProvideListDto(
        Long id,
        String company,
        LocalDate beginDate,
        LocalDate endDate,
        CourseProvideState state,
        Integer attendeeCount
){
    public static CourseProvideListDto from(CourseProvide courseProvide) {
        return CourseProvideListDto.builder()
                .id(courseProvide.getId())
                .company(courseProvide.getCompany().getName())
                .beginDate(courseProvide.getBeginDate())
                .endDate(courseProvide.getEndDate())
                .state(courseProvide.getState())
                .attendeeCount(courseProvide.getAttendeeCount())
                .build();
    }
}
