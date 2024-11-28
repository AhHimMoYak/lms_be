package com.example.ahimmoyakbackend.curriculum.service;

import com.example.ahimmoyakbackend.course.dto.CurriculumCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.CurriculumCreateResponseDto;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumListByInstitutionResponseDto;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CurriculumService {
    CurriculumCreateResponseDto add(UserDetails userDetails, long courseId, CurriculumCreateRequestDto requestDto);
    MessageResponseDto update(UserDetails userDetails, long curriculumId, String curriculumTitle);
    MessageResponseDto delete(UserDetails userDetails, long curriculumId);

    List<CurriculumListByInstitutionResponseDto> get(UserDetails userDetails, Long courseId);
}
