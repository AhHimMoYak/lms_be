package click.ahimmoyak.studentservice.course.repository;

import click.ahimmoyak.studentservice.course.entity.Contents;
import click.ahimmoyak.studentservice.course.entity.ContentsHistory;
import click.ahimmoyak.studentservice.course.entity.Course;
import click.ahimmoyak.studentservice.course.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentsHistoryRepository extends JpaRepository<ContentsHistory, Long> {

    Optional<ContentsHistory> findByEnrollmentAndContents(Enrollment enrollment, Contents contents);

    @Query("SELECT COUNT(ch) FROM ContentsHistory ch WHERE ch.enrollment.user.id = :userId AND ch.contents.id IN :contentIds AND ch.state = 'COMPLETED'")
    Long countCompletedContentByUserIdAndContentIds(@Param("userId") Long userId, @Param("contentIds") List<String> contentIds);

    List<ContentsHistory> findByEnrollmentAndContents_Curriculum_Course(Enrollment enrollment, Course course);


}
