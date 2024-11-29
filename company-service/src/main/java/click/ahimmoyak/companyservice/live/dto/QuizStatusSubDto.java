package click.ahimmoyak.companyservice.live.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record QuizStatusSubDto(
        long quizId,
        List<QuizStatusOptionSubDto> options
) {
}
