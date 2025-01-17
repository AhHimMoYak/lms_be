package click.ahimmoyak.institutionservice.course.entity;

import click.ahimmoyak.institutionservice.course.common.ContentType;
import click.ahimmoyak.institutionservice.course.dto.GetContentsRequestDto;
import click.ahimmoyak.institutionservice.global.entity.Timestamped;
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

    public Contents patch(GetContentsRequestDto requestDto, Curriculum curriculum) {
        if (requestDto.contentTitle() != null) {
            this.title = requestDto.contentTitle();
        }
        if (requestDto.contentType() != null) {
            this.type = requestDto.contentType();
        }
        if (requestDto.idx() != null) {
            this.idx = requestDto.idx();
        }
        if (requestDto.s3Url() != null) {
            this.s3Url = requestDto.s3Url();
        }

        if (curriculum != null) {
            this.curriculum = curriculum;
        }

        return this;
    }

}
