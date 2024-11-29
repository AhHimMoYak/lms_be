package click.ahimmoyak.studentservice.live.service;

import click.ahimmoyak.studentservice.course.repository.AttendHistoryRepository;
import click.ahimmoyak.studentservice.course.repository.EnrollmentRepository;
import click.ahimmoyak.studentservice.live.repository.ChatAttendRepository;
import click.ahimmoyak.studentservice.live.repository.ChatMessageRepository;
import click.ahimmoyak.studentservice.live.repository.LiveStatusRepository;
import click.ahimmoyak.studentservice.live.repository.LiveStreamingRepository;
import click.ahimmoyak.studentservice.live.dto.ChatMessagePubDto;
import click.ahimmoyak.studentservice.live.dto.ChatMessageSubDto;
import click.ahimmoyak.studentservice.live.dto.LiveJoinDto;
import click.ahimmoyak.studentservice.live.entity.AttendHistory;
import click.ahimmoyak.studentservice.live.entity.ChatAttend;
import click.ahimmoyak.studentservice.live.entity.ChatMessage;
import click.ahimmoyak.studentservice.live.entity.LiveStreaming;
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
