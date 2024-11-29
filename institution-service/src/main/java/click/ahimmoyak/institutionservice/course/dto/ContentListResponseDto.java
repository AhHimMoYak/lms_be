package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.ContentType;
import click.ahimmoyak.institutionservice.course.entity.Contents;
import lombok.Builder;

@Builder
public record ContentListResponseDto(
//    long id,
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
