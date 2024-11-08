package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.dto.ContentUpdateRequestDto;
import com.example.ahimmoyakbackend.course.dto.ContentsCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.ContentsInfoResponseDto;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface ContentsService {
    MessageResponseDto add(UserDetails userDetails, long curriculumId, ContentsCreateRequestDto requestDto);
    ContentsInfoResponseDto getInfo(long contentsId);
    MessageResponseDto Update(UserDetails userDetails, Long curriculumId, ContentUpdateRequestDto requestDto);
}
