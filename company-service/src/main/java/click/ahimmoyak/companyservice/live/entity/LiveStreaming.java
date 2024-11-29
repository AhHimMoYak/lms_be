package click.ahimmoyak.companyservice.live.entity;

import click.ahimmoyak.companyservice.course.entity.CourseProvide;
import click.ahimmoyak.companyservice.global.entity.Timestamped;
import click.ahimmoyak.companyservice.live.common.LiveState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "live_streaming")
public class LiveStreaming extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    @Enumerated(EnumType.STRING)
    private LiveState state;

    @Setter
    @ManyToOne
    @JoinColumn(name = "course_provide_id")
    private CourseProvide courseProvide;


    public void setState(LiveState state) {
        this.state = state;
    }

}