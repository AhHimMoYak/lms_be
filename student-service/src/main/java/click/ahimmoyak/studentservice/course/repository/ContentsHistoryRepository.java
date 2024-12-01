package click.ahimmoyak.studentservice.course.repository;

import click.ahimmoyak.studentservice.course.entity.Contents;
import click.ahimmoyak.studentservice.course.entity.ContentsHistory;
import click.ahimmoyak.studentservice.course.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentsHistoryRepository extends JpaRepository<ContentsHistory, Long> {

    Optional<ContentsHistory> findByEnrollmentAndContents(Enrollment enrollment, Contents contents);
}
