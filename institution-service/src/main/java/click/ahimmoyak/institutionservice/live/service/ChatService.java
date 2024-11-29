package click.ahimmoyak.institutionservice.live.service;

import click.ahimmoyak.institutionservice.course.repository.AttendHistoryRepository;
import click.ahimmoyak.institutionservice.course.repository.EnrollmentRepository;
import click.ahimmoyak.institutionservice.live.dto.ChatMessagePubDto;
import click.ahimmoyak.institutionservice.live.dto.ChatMessageSubDto;
import click.ahimmoyak.institutionservice.live.dto.LiveJoinDto;
import click.ahimmoyak.institutionservice.live.repository.ChatAttendRepository;
import click.ahimmoyak.institutionservice.live.repository.ChatMessageRepository;
import click.ahimmoyak.institutionservice.live.repository.LiveStatusRepository;
import click.ahimmoyak.institutionservice.live.repository.LiveStreamingRepository;
import click.ahimmoyak.institutionservice.live.entity.AttendHistory;
import click.ahimmoyak.institutionservice.live.entity.ChatAttend;
import click.ahimmoyak.institutionservice.live.entity.ChatMessage;
import click.ahimmoyak.institutionservice.live.entity.LiveStreaming;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final LiveStatusRepository liveStatusRepository;
    private final ChatAttendRepository chatAttendRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LiveStreamingRepository liveStreamingRepository;
    private final AttendHistoryRepository attendHistoryRepository;

    @Transactional
    public ChatMessageSubDto message(long liveId, ChatMessagePubDto dto) {
        if (!liveStatusRepository.existsById(liveId)) {
            return null;
        }
        ChatMessage chatMessage = chatMessageRepository.save(dto.to(liveId));
        return ChatMessageSubDto.from(chatMessage);
    }

    @Transactional
    public List<ChatMessageSubDto> getAll(long liveId) {
        return chatMessageRepository.findAllByLiveIdOrderByTimeAsc(liveId).stream()
                .map(ChatMessageSubDto::from)
                .toList();
    }

    @Transactional
    public void deleteAll(long liveId) {
        List<ChatMessage> messages = chatMessageRepository.findAllByLiveIdOrderByTimeAsc(liveId);
        chatMessageRepository.deleteAll(messages);
    }

    @Transactional
    public void attend(long liveId, LiveJoinDto dto) {
        if(!liveStatusRepository.existsById(liveId)){return;}
        ChatAttend chatAttend = chatAttendRepository.findByLiveIdAndUsername(liveId, dto.username());
        if(chatAttend != null) {return;}
        chatAttendRepository.save(ChatAttend.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .liveId(liveId)
                .username(dto.username())
                .build());
    }

    @Transactional
    public void saveAttendHistory(long liveId) {
        List<ChatAttend> chatAttends = chatAttendRepository.findAllByLiveId(liveId);
        LiveStreaming live = liveStreamingRepository.findById(liveId).orElse(null);
        if(live == null) {return;}
        // Todo 라이브 참여기록 저장시 인롤먼트 불러올때 코스가 아닌 코스프로바이드로 찾아오도록 수정
//        Course course = live.getCourse();
        attendHistoryRepository.saveAll(chatAttends.stream()
                .map(attend -> AttendHistory.builder()
//                        .enrollment(enrollmentRepository.findByUser_UsernameAndCourse(attend.getUsername(), course))
                        .liveStreaming(live)
                        .attendance(true)
                        .build()).toList());
        chatAttendRepository.deleteAll(chatAttends);
    }
}
