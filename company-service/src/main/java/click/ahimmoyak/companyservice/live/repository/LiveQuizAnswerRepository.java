package click.ahimmoyak.companyservice.live.repository;

import click.ahimmoyak.companyservice.live.entity.LiveQuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveQuizAnswerRepository extends JpaRepository<LiveQuizAnswer, Long> {
}