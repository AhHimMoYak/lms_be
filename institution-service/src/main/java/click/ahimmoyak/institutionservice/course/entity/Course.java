package click.ahimmoyak.institutionservice.course.entity;

import click.ahimmoyak.institutionservice.course.common.CourseCategory;
import click.ahimmoyak.institutionservice.course.common.CourseState;
import click.ahimmoyak.institutionservice.course.dto.CourseCreateRequestDto;
import click.ahimmoyak.institutionservice.global.entity.Timestamped;
import click.ahimmoyak.institutionservice.institution.entity.Institution;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String introduction;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CourseCategory category;

    @Column
    private String instructor;

    @Column
    @Enumerated(value = EnumType.STRING)
    private CourseState state;

    @OneToMany(mappedBy = "course")
    private List<Curriculum> curriculumList;


    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Course patch(CourseCreateRequestDto requestDto) {
        this.title = requestDto.title();
        this.introduction = requestDto.introduction();
        this.category = requestDto.category();
        return this;
    }

    public Course setState(CourseState state) {
        this.state = state;
        return this;
    }
}
