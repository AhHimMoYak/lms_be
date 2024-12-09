package click.ahimmoyak.studentservice.course.repository;

import click.ahimmoyak.studentservice.course.entity.Contents;
import click.ahimmoyak.studentservice.course.entity.ContentsMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsMaterialRepository extends JpaRepository<ContentsMaterial, Long> {

    ContentsMaterial findBySavedName(String savedName);
}