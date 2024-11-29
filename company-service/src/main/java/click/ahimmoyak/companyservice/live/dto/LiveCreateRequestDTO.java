package click.ahimmoyak.companyservice.live.dto;

import click.ahimmoyak.companyservice.live.common.LiveState;
import click.ahimmoyak.companyservice.live.entity.LiveStreaming;
import click.ahimmoyak.companyservice.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiveCreateRequestDTO {
    private String title;
    private LocalDateTime startTime;

    // Todo 라이브 생성시 코스가 아닌 코스프로바이드 정보 받도록 수정해야함
    public LiveStreaming toEntity(Course course) {
        return LiveStreaming.builder()
                .title(title)
                .startTime(startTime)
                .endTime(null)
                .state(LiveState.CREATED)
//                .course(course)
                .build();
    }
}
