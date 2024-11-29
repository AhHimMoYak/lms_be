package click.ahimmoyak.companyservice.live.dto;

import lombok.Builder;

@Builder
public record QuizStatusOptionSubDto (
        int idx,
        int count
) {
}
