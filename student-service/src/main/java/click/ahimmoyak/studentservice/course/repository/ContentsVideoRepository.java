package click.ahimmoyak.studentservice.course.repository;

import click.ahimmoyak.studentservice.course.entity.Contents;
import click.ahimmoyak.studentservice.course.entity.ContentsVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsVideoRepository extends JpaRepository<ContentsVideo, Long> {
    ContentsVideo findByContents(Contents contents);
}