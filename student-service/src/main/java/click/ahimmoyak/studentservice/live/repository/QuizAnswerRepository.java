package click.ahimmoyak.studentservice.live.repository;

import click.ahimmoyak.studentservice.live.entity.QuizAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAnswerRepository extends CrudRepository<QuizAnswer, Long> {
    public List<QuizAnswer> findAllByQuizId(Long quizId);
    public List<QuizAnswer> findAllByLiveId(Long liveId);
}
