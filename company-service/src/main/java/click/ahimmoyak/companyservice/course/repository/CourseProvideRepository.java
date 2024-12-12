package click.ahimmoyak.companyservice.course.repository;

import click.ahimmoyak.companyservice.course.common.CourseProvideState;
import click.ahimmoyak.companyservice.course.entity.CourseProvide;
import click.ahimmoyak.companyservice.institution.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CourseProvideRepository extends JpaRepository<CourseProvide, Long> {
    List<CourseProvide> findByCompany_Id(Long id);

    List<CourseProvide> findAllByBeginDateAndState(LocalDate beginDate, CourseProvideState state);

    List<CourseProvide> findAllByEndDateAndState(LocalDate endDate, CourseProvideState state);

    int countByCompany_IdAndState(Long id, CourseProvideState state);

}

