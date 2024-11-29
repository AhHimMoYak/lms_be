package click.ahimmoyak.studentservice.live.repository;

import click.ahimmoyak.studentservice.live.entity.LiveStreaming;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveStreamingRepository extends JpaRepository<LiveStreaming, Long> {
//    List<LiveStreaming> findByCourse(Course course);

//    List<LiveStreaming> findByCourse_Tutor(User tutor);

}