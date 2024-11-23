package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.course.dto.CurriculumCreateRequestDto;
import com.example.ahimmoyakbackend.course.service.CurriculumService;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course/{courseId}")
public class CurriculumController {

    private final CurriculumService curriculumService;

    @PostMapping("/curriculum")
    public ResponseEntity<Long> addCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("courseId") long courseId,
            @RequestBody CurriculumCreateRequestDto requestDto
            ) {
        return ResponseEntity.ok(curriculumService.add(userDetails, courseId, requestDto.title()));
    }

    @PatchMapping("/curriculum/{curriculumId}")
    public ResponseEntity<MessageResponseDto> updateCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("curriculumId") long curriculumId,
            @RequestBody CurriculumCreateRequestDto requestDto,
            @PathVariable String courseId) {

        return ResponseEntity.ok(curriculumService.update(userDetails, curriculumId, requestDto.title()));
    }

    @DeleteMapping("/curriculum/{curriculumId}")
    public ResponseEntity<MessageResponseDto> deleteCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("curriculumId") long curriculumId,
            @PathVariable String courseId) {
        return ResponseEntity.ok(curriculumService.delete(userDetails, curriculumId));
    }
}
