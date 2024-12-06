package click.ahimmoyak.studentservice.course.repository;

import click.ahimmoyak.studentservice.auth.entity.User;
import click.ahimmoyak.studentservice.course.common.CourseProvideState;
import click.ahimmoyak.studentservice.course.entity.CourseProvide;
import click.ahimmoyak.studentservice.institution.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CourseProvideRepository extends JpaRepository<CourseProvide, Long> {

    List<CourseProvide> findAllByBeginDateAndState(LocalDate beginDate, CourseProvideState state);

    List<CourseProvide> findAllByEndDateAndState(LocalDate endDate, CourseProvideState state);

    List<CourseProvide> findByEnrollments_User(User user);
}

