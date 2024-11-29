package click.ahimmoyak.institutionservice.live.repository;

import click.ahimmoyak.institutionservice.live.entity.QuizStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizStatusRepository extends CrudRepository<QuizStatus, Long> {
    List<QuizStatus> findAllByLiveId(Long liveId);
}
