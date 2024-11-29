package click.ahimmoyak.institutionservice.course.repository;

import click.ahimmoyak.institutionservice.course.entity.Contents;
import click.ahimmoyak.institutionservice.course.entity.ContentsVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsVideoRepository extends JpaRepository<ContentsVideo, Long> {
    ContentsVideo findByContents(Contents contents);
}