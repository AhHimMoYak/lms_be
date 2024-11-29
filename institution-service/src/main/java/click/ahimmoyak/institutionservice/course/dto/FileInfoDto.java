package click.ahimmoyak.institutionservice.course.dto;

import lombok.Builder;

@Builder
public record FileInfoDto(
        String path,
        String originName,
        String savedName,
        String postfix,
        Double duration
) {
}
