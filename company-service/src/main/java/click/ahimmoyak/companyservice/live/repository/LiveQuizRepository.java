package click.ahimmoyak.companyservice.live.repository;

import click.ahimmoyak.companyservice.live.entity.LiveQuiz;
import click.ahimmoyak.companyservice.live.entity.LiveStreaming;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiveQuizRepository extends JpaRepository<LiveQuiz, Long> {
    List<LiveQuiz> findByLiveStreaming(LiveStreaming liveStreaming);
}