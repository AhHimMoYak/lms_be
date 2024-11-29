package click.ahimmoyak.studentservice.course.dto;

import lombok.Builder;

@Builder
public record CurriculumCreateRequestDto(
        String title
) {
}
