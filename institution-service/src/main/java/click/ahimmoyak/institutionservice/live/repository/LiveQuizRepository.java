package click.ahimmoyak.institutionservice.live.repository;

import click.ahimmoyak.institutionservice.live.entity.LiveQuiz;
import click.ahimmoyak.institutionservice.live.entity.LiveStreaming;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiveQuizRepository extends JpaRepository<LiveQuiz, Long> {
    List<LiveQuiz> findByLiveStreaming(LiveStreaming liveStreaming);
}