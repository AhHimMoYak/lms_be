package click.ahimmoyak.institutionservice.course.repository;

import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import click.ahimmoyak.institutionservice.institution.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CourseProvideRepository extends JpaRepository<CourseProvide, Long> {

    List<CourseProvide> findAllByInstitution(Institution institution);

    List<CourseProvide> findAllByBeginDateAndState(LocalDate beginDate, CourseProvideState state);

    List<CourseProvide> findAllByEndDateAndState(LocalDate endDate, CourseProvideState state);
}

