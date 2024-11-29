package click.ahimmoyak.studentservice.live.repository;

import click.ahimmoyak.studentservice.live.entity.LiveQuiz;
import click.ahimmoyak.studentservice.live.entity.LiveStreaming;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiveQuizRepository extends JpaRepository<LiveQuiz, Long> {
    List<LiveQuiz> findByLiveStreaming(LiveStreaming liveStreaming);
}