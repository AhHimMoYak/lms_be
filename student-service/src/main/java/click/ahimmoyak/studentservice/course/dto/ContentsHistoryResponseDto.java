package click.ahimmoyak.studentservice.course.dto;

import click.ahimmoyak.studentservice.course.common.ContentsHistoryState;
import click.ahimmoyak.studentservice.course.entity.ContentsHistory;
import click.ahimmoyak.studentservice.course.entity.CourseProvide;
import lombok.Builder;

@Builder
public record ContentsHistoryResponseDto(
        String courseTitle,
        ContentsHistoryState state,
        String contents_id
) {
    public static ContentsHistoryResponseDto from(ContentsHistory contentsHistory) {
        return ContentsHistoryResponseDto.builder()
                .courseTitle(contentsHistory.getEnrollment().getCourseProvide().getCourse().getTitle())
                .state(contentsHistory.getState())
                .contents_id(contentsHistory.getContents().getId())
                .build();
    }
}
