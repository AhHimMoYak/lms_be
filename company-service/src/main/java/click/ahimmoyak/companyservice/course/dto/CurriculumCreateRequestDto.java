package click.ahimmoyak.companyservice.course.dto;

import lombok.Builder;

@Builder
public record CurriculumCreateRequestDto(
        String title
) {
}
