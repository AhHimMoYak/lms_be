package click.ahimmoyak.studentservice.course.repository;

import click.ahimmoyak.studentservice.course.entity.Contents;
import click.ahimmoyak.studentservice.course.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
    public long countByCurriculum(Curriculum curriculum);
}