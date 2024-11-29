package click.ahimmoyak.companyservice.course.repository;

import click.ahimmoyak.companyservice.course.entity.Contents;
import click.ahimmoyak.companyservice.course.entity.ContentsVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsVideoRepository extends JpaRepository<ContentsVideo, Long> {
    ContentsVideo findByContents(Contents contents);
}