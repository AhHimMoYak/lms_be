package click.ahimmoyak.studentservice.course.dto;

import click.ahimmoyak.studentservice.course.common.ContentType;
import click.ahimmoyak.studentservice.course.entity.Contents;
import lombok.Builder;

@Builder
public record ContentListResponseDto(
    String id,
    ContentType type,
    String title,
    String videoDurations
) {
    public static ContentListResponseDto from(Contents contents) {
        return ContentListResponseDto.builder()
                .id(contents.getId())
                .type(contents.getType())
                .title(contents.getTitle())
                .build();
    }
}
