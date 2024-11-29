package click.ahimmoyak.studentservice.live.repository;

import click.ahimmoyak.studentservice.live.entity.LiveQuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveQuizAnswerRepository extends JpaRepository<LiveQuizAnswer, Long> {
}