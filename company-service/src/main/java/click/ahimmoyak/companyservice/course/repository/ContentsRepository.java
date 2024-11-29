package click.ahimmoyak.companyservice.course.repository;

import click.ahimmoyak.companyservice.course.entity.Contents;
import click.ahimmoyak.companyservice.course.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
    public long countByCurriculum(Curriculum curriculum);
}