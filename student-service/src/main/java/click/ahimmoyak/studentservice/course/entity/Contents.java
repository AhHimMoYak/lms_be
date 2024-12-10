package click.ahimmoyak.studentservice.course.entity;

import click.ahimmoyak.studentservice.course.common.ContentType;
import click.ahimmoyak.studentservice.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contents")
public class Contents extends Timestamped {

    @Id
    private String id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private ContentType type;

    @Column(nullable = false)
    private Integer idx;

    @Column(length = 255)
    private String s3Url;

    @Column(length = 255, name = "video_duration")
    private String videoDuration;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

    @Builder.Default
    @Setter
    @OneToMany(mappedBy = "contents")
    private List<ContentsHistory> contentsHistoryArrayList = new ArrayList<>();


}


