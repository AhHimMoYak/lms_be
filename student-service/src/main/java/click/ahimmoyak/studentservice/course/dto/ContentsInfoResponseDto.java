package click.ahimmoyak.studentservice.course.dto;

import click.ahimmoyak.studentservice.course.entity.Contents;
import click.ahimmoyak.studentservice.course.common.ContentType;
import lombok.Builder;

@Builder
public record ContentsInfoResponseDto(
        String title,
        ContentType contentType,
        String fileInfo
) {
    public static ContentsInfoResponseDto from(Contents contents, String fileInfo) {
        return ContentsInfoResponseDto.builder()
                .title(contents.getTitle())
                .contentType(contents.getType())
                .fileInfo(fileInfo)
                .build();
    }
}
