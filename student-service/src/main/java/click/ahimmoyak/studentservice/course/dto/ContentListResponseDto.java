package click.ahimmoyak.studentservice.course.dto;

import click.ahimmoyak.studentservice.course.common.ContentType;
import click.ahimmoyak.studentservice.course.common.ContentsHistoryState;
import click.ahimmoyak.studentservice.course.entity.Contents;
import lombok.Builder;

import java.util.Stack;

@Builder
public record ContentListResponseDto(
    String id,
    ContentType type,
    String title,
    String videoDurations,
    ContentsHistoryState state
) {
    public static ContentListResponseDto from(Contents contents) {
        return ContentListResponseDto.builder()
                .id(contents.getId())
                .type(contents.getType())
                .title(contents.getTitle())
                .build();
    }
}
