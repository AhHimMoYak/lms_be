package click.ahimmoyak.institutionservice.live.dto;

import lombok.Builder;

@Builder
public record QuizStatusOptionSubDto (
        int idx,
        int count
) {
}
