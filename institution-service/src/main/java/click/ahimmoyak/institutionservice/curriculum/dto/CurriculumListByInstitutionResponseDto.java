package click.ahimmoyak.institutionservice.curriculum.dto;

import lombok.Builder;

@Builder
public record CurriculumListByInstitutionResponseDto(
        Long curriculumId,
        String title,
        Integer idx
) {
}
