package click.ahimmoyak.companyservice.course.repository;

import click.ahimmoyak.companyservice.course.entity.Contents;
import click.ahimmoyak.companyservice.course.entity.ContentsMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsMaterialRepository extends JpaRepository<ContentsMaterial, Long> {

    ContentsMaterial findBySavedName(String savedName);
}