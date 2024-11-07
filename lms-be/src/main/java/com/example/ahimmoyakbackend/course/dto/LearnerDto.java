package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.auth.entity.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record LearnerDto(
        Long enrollmentId,
        String username,
        String name,
        String email,
        LocalDate birth
) {
    public static LearnerDto from(Long enrollmentId, User user) {
        return LearnerDto.builder()
                .enrollmentId(enrollmentId)
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .birth(user.getBirth())
                .build();
    }
}
