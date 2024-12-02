package click.ahimmoyak.studentservice.course.dto;

import click.ahimmoyak.studentservice.course.common.ContentType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ContentDetailResponseDto(
        String contentId,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate,
        int idx,
        String tittle,
        ContentType contentType,
        Long curriculumId
) {
}
