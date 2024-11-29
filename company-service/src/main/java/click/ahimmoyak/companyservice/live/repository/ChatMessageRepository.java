package click.ahimmoyak.companyservice.live.repository;

import click.ahimmoyak.companyservice.live.entity.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByLiveIdOrderByTimeAsc(Long roomId);
    void deleteAllByLiveId(Long liveId);
}
