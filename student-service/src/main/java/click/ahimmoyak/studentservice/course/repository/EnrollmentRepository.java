package click.ahimmoyak.studentservice.course.repository;

import click.ahimmoyak.studentservice.auth.entity.User;
import click.ahimmoyak.studentservice.course.entity.CourseProvide;
import click.ahimmoyak.studentservice.course.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Override
    boolean existsById(Long id);

    boolean existsByUserAndCourseProvide(User user, CourseProvide courseProvide);

//    Optional<Enrollment> findByUserAndCourse(User user, Course course);

//    Optional<Enrollment> findByUserIdAndCourseId(Long id, Long id1);

    //    Enrollment findByUser_UsernameAndCourse(String username, Course course);
    List<Enrollment> findAllByCourseProvide_Id(Long id);

    List<Enrollment> findByUser_Name(String name);

    @Query("SELECT e FROM Enrollment e "
            + "JOIN e.courseProvide cp "
            + "JOIN cp.course c "
            + "JOIN c.curriculumList cu "
            + "JOIN cu.contentsList cont "
            + "WHERE e.user = :user AND cont.id = :contentId")
    List<Enrollment> findEnrollmentsByUserAndContent(@Param("user") User user, @Param("contentId") String contentId);

    Optional<Enrollment> findByUserAndCourseProvide(User user, CourseProvide courseProvide);
}