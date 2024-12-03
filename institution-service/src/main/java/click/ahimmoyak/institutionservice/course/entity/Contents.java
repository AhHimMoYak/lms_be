package click.ahimmoyak.institutionservice.course.entity;

import click.ahimmoyak.institutionservice.course.common.ContentType;
import click.ahimmoyak.institutionservice.course.dto.GetContentsRequestDto;
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

    @Column(nullable = false)
    private String videoDuration;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

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
