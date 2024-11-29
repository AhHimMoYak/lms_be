package click.ahimmoyak.institutionservice.course.entity;

import click.ahimmoyak.institutionservice.course.common.ContentsHistoryState;
import click.ahimmoyak.institutionservice.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contents_history")
public class ContentsHistory extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    //@ColumnDefault("NOT_STARTED")
    @Enumerated(EnumType.STRING)
    private ContentsHistoryState state;

    @Column
    private Long progress; // 이게 커리큘럼프로그래스

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "contents_id")
    private Contents contents;

}