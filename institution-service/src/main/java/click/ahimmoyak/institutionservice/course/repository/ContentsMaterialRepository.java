package click.ahimmoyak.institutionservice.course.repository;

import click.ahimmoyak.institutionservice.course.entity.Contents;
import click.ahimmoyak.institutionservice.course.entity.ContentsMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsMaterialRepository extends JpaRepository<ContentsMaterial, Long> {
    ContentsMaterial findByContents(Contents contents);

    ContentsMaterial findBySavedName(String savedName);
}