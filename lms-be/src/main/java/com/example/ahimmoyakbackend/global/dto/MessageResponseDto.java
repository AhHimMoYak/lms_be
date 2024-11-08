package com.example.ahimmoyakbackend.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public record MessageResponseDto(
        String message
) {
    public static MessageResponseDto of(String message) {
        return new MessageResponseDto(message);
    }
}
