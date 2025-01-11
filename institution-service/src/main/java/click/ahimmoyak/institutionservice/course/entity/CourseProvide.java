package click.ahimmoyak.institutionservice.course.entity;

import click.ahimmoyak.institutionservice.company.entity.Company;
import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import click.ahimmoyak.institutionservice.global.entity.Timestamped;
import click.ahimmoyak.institutionservice.institution.entity.Institution;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_provide")
public class CourseProvide extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private LocalDate beginDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CourseProvideState state;

    @Column(nullable = false)
    private int attendeeCount;

    @Column
    private long deposit;

    @Setter
    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Builder.Default
    @OneToMany(mappedBy = "courseProvide")
    private List<Enrollment> enrollments = new ArrayList<>();


    public LocalDate getProvisionPeriod() {
        return beginDate.plusDays(course.getPeriod());
    }

    public void updateCourseProvideState(CourseProvideState state) {
        if (state != null) {
            this.state = state;
        }
    }
    public void reject() {
        this.state = CourseProvideState.DECLINED;
    }

    public void accept() {
        this.state = CourseProvideState.ACCEPTED;
    }

    public void startCourse() {
        if (this.state == CourseProvideState.NOT_STARTED) {
            this.state = CourseProvideState.ONGOING;
        }
    }

    public void finishCourse() {
        if (this.state == CourseProvideState.ONGOING) {
            this.state = CourseProvideState.FINISHED;
        }
    }

    public void setState() {
        this.state = CourseProvideState.NOT_STARTED;
    }

    public boolean deny() {
        return false;
    }
}
