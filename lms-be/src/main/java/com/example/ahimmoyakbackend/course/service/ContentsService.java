package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.dto.ContentsCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.ContentsInfoResponseDto;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface ContentsService {
    public MessageResponseDto add(UserDetails userDetails, long curriculumId, ContentsCreateRequestDto requestDto);
    public ContentsInfoResponseDto getInfo(long contentsId);
}
