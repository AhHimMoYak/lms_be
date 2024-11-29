package click.ahimmoyak.studentservice.course.entity;

import click.ahimmoyak.studentservice.global.entity.Timestamped;
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
@Table(name = "image")
public class Image extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 255)
    private String path;

    @Column(name = "origin_name", nullable = false, length = 255)
    private String originName;

    @Column(name = "saved_name", nullable = false, length = 255)
    private String savedName;

    @Column(nullable = false, length = 20)
    private String postfix;


}
