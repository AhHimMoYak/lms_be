package click.ahimmoyak.institutionservice.course.dto;

import click.ahimmoyak.institutionservice.course.common.ContentType;
import lombok.Builder;

@Builder
public record GetContentsRequestDto(
        String contentId,
        String contentTitle,
        Integer idx,
        ContentType contentType,
        String s3Url
) {
}
