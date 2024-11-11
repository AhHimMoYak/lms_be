package com.example.ahimmoyakbackend.course.entity;

import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.course.common.CourseProvideState;
import com.example.ahimmoyakbackend.course.common.EnrollmentState;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import com.example.ahimmoyakbackend.institution.entity.Institution;
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
    private CourseProvideState state;

    @Column(nullable = false)
    private int attendeeCount;

    @Column
    private long deposit;

    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Builder.Default
    @OneToMany(mappedBy = "courseProvide")
    private List<Enrollment> enrollments = new ArrayList<>();


    public void updateCourseProvideState(CourseProvideState state) {
        if (state != null) {
            this.state = state;
        }
    }
}
