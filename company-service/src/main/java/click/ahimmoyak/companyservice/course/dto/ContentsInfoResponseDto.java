package click.ahimmoyak.companyservice.course.dto;

import click.ahimmoyak.companyservice.course.common.ContentType;
import click.ahimmoyak.companyservice.course.entity.Contents;
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
