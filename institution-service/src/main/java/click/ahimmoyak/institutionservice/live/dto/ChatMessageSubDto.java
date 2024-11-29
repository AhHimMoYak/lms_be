package click.ahimmoyak.institutionservice.live.dto;

import click.ahimmoyak.institutionservice.live.entity.ChatMessage;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatMessageSubDto(
        String username,
        String message,
        LocalDateTime time

) {
    public static ChatMessageSubDto from(ChatMessage chatMessage) {
        return ChatMessageSubDto.builder()
                .username(chatMessage.getUsername())
                .message(chatMessage.getMessage())
                .time(chatMessage.getTime())
                .build();
    }
}
