package click.ahimmoyak.companyservice.course.repository;

import click.ahimmoyak.companyservice.course.entity.Course;
import click.ahimmoyak.companyservice.course.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    long countByCourse(Course course);
}