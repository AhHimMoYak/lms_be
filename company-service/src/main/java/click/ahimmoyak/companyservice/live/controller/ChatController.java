package click.ahimmoyak.companyservice.live.controller;

import click.ahimmoyak.companyservice.live.dto.ChatMessagePubDto;
import click.ahimmoyak.companyservice.live.dto.ChatMessageSubDto;
import click.ahimmoyak.companyservice.live.dto.LiveJoinDto;
import click.ahimmoyak.companyservice.live.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chats/{liveId}")
    public ResponseEntity<ChatMessageSubDto> message(@DestinationVariable long liveId, ChatMessagePubDto dto) {
        ChatMessageSubDto subDto = chatService.message(liveId, dto);
        if (subDto == null) {
            return ResponseEntity.notFound().build();
        }
        messagingTemplate.convertAndSend("/sub/chat/" + liveId, subDto);
        return ResponseEntity.ok(subDto);
    }

    @GetMapping("/api/v1/lives/{liveId}/chats")
    public ResponseEntity<List<ChatMessageSubDto>> getAllMessage(@PathVariable long liveId) {
        return ResponseEntity.ok(chatService.getAll(liveId));
    }

    @MessageMapping("/join/{liveId}")
    public void attendChat(@DestinationVariable long liveId, LiveJoinDto dto) {
        log.info("chatting: " + dto.username() + " joined");
        chatService.attend(liveId, dto);
    }
}
