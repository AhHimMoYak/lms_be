package com.example.ahimmoyakbackend.curriculum.controller;


import com.example.ahimmoyakbackend.course.dto.CurriculumCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.CurriculumCreateResponseDto;
import com.example.ahimmoyakbackend.curriculum.service.CurriculumService;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses/{courseId}")
public class CurriculumController {

    private final CurriculumService curriculumService;

    @PostMapping("/curriculums")
    public ResponseEntity<CurriculumCreateResponseDto> addCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("courseId") long courseId,
            @RequestBody CurriculumCreateRequestDto requestDto
    ) {
        return ResponseEntity.ok(curriculumService.add(userDetails, courseId, requestDto)) ;
    }

    @PatchMapping("/curriculums/{curriculumId}")
    public ResponseEntity<MessageResponseDto> updateCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("curriculumId") long curriculumId,
            @RequestBody CurriculumCreateRequestDto requestDto
    ) {
        return ResponseEntity.ok(curriculumService.update(userDetails, curriculumId, requestDto.title()));
    }

    @DeleteMapping("/curriculums/{curriculumId}")
    public ResponseEntity<MessageResponseDto> deleteCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("curriculumId") long curriculumId
    ) {
        return ResponseEntity.ok(curriculumService.delete(userDetails, curriculumId));
    }
}
