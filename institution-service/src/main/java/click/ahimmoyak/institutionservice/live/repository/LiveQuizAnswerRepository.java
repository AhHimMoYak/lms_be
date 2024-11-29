package click.ahimmoyak.institutionservice.live.repository;

import click.ahimmoyak.institutionservice.live.entity.LiveQuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveQuizAnswerRepository extends JpaRepository<LiveQuizAnswer, Long> {
}