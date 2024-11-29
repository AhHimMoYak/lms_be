package click.ahimmoyak.studentservice.live.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record QuizStatusSubDto(
        long quizId,
        List<QuizStatusOptionSubDto> options
) {
}
