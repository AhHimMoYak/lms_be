package click.ahimmoyak.studentservice.global.dto;

import lombok.Builder;

@Builder
public record MessageResponseDto(
        String message
) {
    public static MessageResponseDto of(String message) {
        return new MessageResponseDto(message);
    }
}
