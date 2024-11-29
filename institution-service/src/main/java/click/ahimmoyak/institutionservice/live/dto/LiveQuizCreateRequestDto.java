package click.ahimmoyak.institutionservice.live.dto;

import click.ahimmoyak.institutionservice.live.entity.LiveQuiz;
import click.ahimmoyak.institutionservice.live.entity.LiveStreaming;
import lombok.Builder;

import java.util.List;

@Builder
public record LiveQuizCreateRequestDto (
        String question,
        int answer,
        String solution,
        List<LiveQuizOptionDto> options
) {
    public LiveQuiz toEntity(LiveStreaming live) {
        return LiveQuiz.builder()
                .liveStreaming(live)
                .question(this.question)
                .answer(this.answer)
                .solution(this.solution)
                .build();
    }
}
