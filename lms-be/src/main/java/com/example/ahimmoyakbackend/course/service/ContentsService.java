package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.dto.ContentUpdateRequestDto;
import com.example.ahimmoyakbackend.course.dto.ContentsCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.ContentsInfoResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface ContentsService {
    boolean add(UserDetails userDetails, long curriculumId, ContentsCreateRequestDto requestDto);
    ContentsInfoResponseDto getInfo(long contentsId);
    String Update(UserDetails userDetails, Long curriculumId, ContentUpdateRequestDto requestDto);
}
