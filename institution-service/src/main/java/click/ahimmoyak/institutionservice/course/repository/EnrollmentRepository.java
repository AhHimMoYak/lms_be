package click.ahimmoyak.institutionservice.course.repository;

import click.ahimmoyak.institutionservice.auth.entity.User;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import click.ahimmoyak.institutionservice.course.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Override
    boolean existsById(Long id);

    List<Enrollment> findAllByCourseProvide_Id(Long id);

    List<Enrollment> findByUser_Username(String username);
}