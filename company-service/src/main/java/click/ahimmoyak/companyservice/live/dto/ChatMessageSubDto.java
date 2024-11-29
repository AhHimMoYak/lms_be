package click.ahimmoyak.companyservice.live.dto;

import click.ahimmoyak.companyservice.live.entity.ChatMessage;
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
