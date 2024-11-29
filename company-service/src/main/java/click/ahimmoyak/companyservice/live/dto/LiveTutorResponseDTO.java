package click.ahimmoyak.companyservice.live.dto;

import click.ahimmoyak.companyservice.live.common.LiveState;
import click.ahimmoyak.companyservice.live.entity.LiveStreaming;
import click.ahimmoyak.companyservice.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiveTutorResponseDTO {
    private Long key;
    private String title;
    private String course;
    private String instructor;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LiveState state;
    private String streamKey;

    // Todo 튜터에게 라이브 목록 제공해주는 기능을 교육기관 매니저에게 제공해주는것으로 변경해야함
    public static LiveTutorResponseDTO from(LiveStreaming liveStreaming, User tutor) {
        return LiveTutorResponseDTO.builder()
                .key(liveStreaming.getId())
                .title(liveStreaming.getTitle())
//                .course(liveStreaming.getCourse().getTitle())
                .instructor(tutor.getName())
                .startTime(liveStreaming.getStartTime())
                .endTime(liveStreaming.getEndTime())
                .state(liveStreaming.getState())
                .streamKey(liveStreaming.getId()+"_"+tutor.getId())
                .build();
    }
}
