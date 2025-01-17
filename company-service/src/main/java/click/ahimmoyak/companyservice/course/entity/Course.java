package click.ahimmoyak.companyservice.course.entity;

import click.ahimmoyak.companyservice.course.common.CourseCategory;
import click.ahimmoyak.companyservice.course.common.CourseState;
import click.ahimmoyak.companyservice.global.entity.Timestamped;
import click.ahimmoyak.companyservice.institution.entity.Institution;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private List<CourseProvide> courseProvides = new ArrayList<>();

    public Course setState(CourseState state) {
        this.state = state;
        return this;
    }
}
