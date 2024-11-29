package click.ahimmoyak.studentservice.live.repository;

import click.ahimmoyak.studentservice.live.entity.LiveStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveStatusRepository extends CrudRepository<LiveStatus, Long> {
}
