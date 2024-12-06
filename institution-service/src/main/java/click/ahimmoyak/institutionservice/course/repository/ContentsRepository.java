package click.ahimmoyak.institutionservice.course.repository;

import click.ahimmoyak.institutionservice.course.entity.Contents;
import click.ahimmoyak.institutionservice.course.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

    Optional<Contents> findById(String contentId);

    List<Contents> findAllByCurriculumId(Long curriculumId);
}