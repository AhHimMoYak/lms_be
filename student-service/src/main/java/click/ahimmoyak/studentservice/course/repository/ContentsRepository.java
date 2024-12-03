package click.ahimmoyak.studentservice.course.repository;

import click.ahimmoyak.studentservice.course.entity.Contents;
import click.ahimmoyak.studentservice.course.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, String> {
    long countByCurriculum(Curriculum curriculum);

    @Query("SELECT c.id FROM Contents c WHERE c.curriculum.course.id = :courseId")
    List<String> findAllContentIdsByCourseIdAsString(@Param("courseId") Long courseId);
}