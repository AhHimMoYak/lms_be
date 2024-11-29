package click.ahimmoyak.institutionservice.live.dto;

import click.ahimmoyak.institutionservice.live.entity.LiveQuiz;
import click.ahimmoyak.institutionservice.live.entity.LiveQuizOption;
import lombok.Builder;

@Builder
public record LiveQuizOptionDto(
        String text,
        int idx
) {
    public LiveQuizOption toEntity(LiveQuiz liveQuiz) {
        return LiveQuizOption.builder()
                .liveQuiz(liveQuiz)
                .text(this.text)
                .idx(this.idx)
                .build();
    }

    public static LiveQuizOptionDto from(LiveQuizOption liveQuizOption) {
        return LiveQuizOptionDto.builder()
                .text(liveQuizOption.getText())
                .idx(liveQuizOption.getIdx())
                .build();
    }
}
