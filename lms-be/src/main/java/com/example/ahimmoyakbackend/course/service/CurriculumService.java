package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface CurriculumService {
    public Long add(UserDetails userDetails, long courseId, String curriculumTitle);
    public MessageResponseDto update(UserDetails userDetails, long curriculumId, String curriculumTitle);
    public MessageResponseDto delete(UserDetails userDetails, long curriculumId);
}
