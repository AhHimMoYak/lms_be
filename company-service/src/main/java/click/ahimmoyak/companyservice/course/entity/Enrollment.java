package click.ahimmoyak.companyservice.course.entity;

import click.ahimmoyak.companyservice.auth.entity.User;
import click.ahimmoyak.companyservice.course.common.EnrollmentState;
import click.ahimmoyak.companyservice.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enrollment")
public class Enrollment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentState state;

    @Column
    private LocalDateTime certificateDate;

    @Column
    private Long progress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_provide_id", nullable = false)
    private CourseProvide courseProvide;

    public void setState() {
        this.state = EnrollmentState.AVAILABLE;
    }
}

