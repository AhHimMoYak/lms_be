package click.ahimmoyak.companyservice.course.dto;

import click.ahimmoyak.companyservice.course.common.ContentType;
import click.ahimmoyak.companyservice.course.entity.Contents;
import lombok.Builder;

@Builder
public record ContentListResponseDto(
    String id,
    ContentType type,
    String title
) {
    public static ContentListResponseDto from(Contents contents) {
        return ContentListResponseDto.builder()
                .id(contents.getId())
                .type(contents.getType())
                .title(contents.getTitle())
                .build();
    }
}
