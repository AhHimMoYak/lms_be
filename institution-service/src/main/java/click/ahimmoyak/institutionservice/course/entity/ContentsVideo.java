package click.ahimmoyak.institutionservice.course.entity;

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
@Table(name = "contents_video")
public class ContentsVideo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private String savedName;

    @Column(nullable = false)
    private String postfix;

    @Column
    private Long timeAmount;

    @OneToOne
    @JoinColumn(name = "contents_id")
    private Contents contents;

}