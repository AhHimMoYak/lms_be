package click.ahimmoyak.studentservice.course.entity;

import click.ahimmoyak.studentservice.course.common.CourseCategory;
import click.ahimmoyak.studentservice.course.common.CourseState;
import click.ahimmoyak.studentservice.global.entity.Timestamped;
import click.ahimmoyak.studentservice.institution.entity.Institution;
import jakarta.persistence.*;
import lombok.*;

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
    private int period;

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

    public Course setState(CourseState state) {
        this.state = state;
        return this;
    }
}
