package com.example.ahimmoyakbackend.live.controller;

import com.example.ahimmoyakbackend.live.dto.LiveCourseResponseDTO;
import com.example.ahimmoyakbackend.live.dto.LiveCreateRequestDTO;
import com.example.ahimmoyakbackend.live.dto.LivePublishFormDTO;
import com.example.ahimmoyakbackend.live.dto.LiveTutorResponseDTO;
import com.example.ahimmoyakbackend.live.service.LiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lives")
@RequiredArgsConstructor
public class LiveController {

    private final LiveService liveService;

    // Fixme 라이브 생성시 코스가 아닌 코스프로바이드 정보와 연결되도록 로직 변경해야함
    @PostMapping
    public ResponseEntity<Void> createLive(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long courseId, @RequestBody LiveCreateRequestDTO requestDTO) {
        boolean result = liveService.createLive(requestDTO, courseId, userDetails);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{liveId}")
    public ResponseEntity<LiveCourseResponseDTO> getLive(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long liveId) {
        return ResponseEntity.ok(liveService.getLive(liveId));
    }

    // Fixme 라이브 조회시 코스가 아닌 코스프로바이드에 대해서 조회하도록 로직 변경해야함, 그러나 교육기관일 경우를 고려하여 두가지 다 필요할 수 도 있음
    @GetMapping
    public ResponseEntity<List<LiveCourseResponseDTO>> getCourseLives(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long courseProvideId) {
        return ResponseEntity.ok().body(liveService.getLiveListByCourse(courseProvideId));
    }

    // Fixme 강사는 없으므로 매니저가 교육기관에 대한 모든 라이브를 조회하는 경우로 로직 변경해야함
    @GetMapping("/instructors")
    public ResponseEntity<List<LiveTutorResponseDTO>> getTutorLives(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(liveService.getLiveListByTutor(userDetails));
    }

    // Fixme 라이브 시작시 강사아이디가 아닌 교육기관 아이디로 검증하도록 변경해야할듯함
    @PostMapping("/publish")
    public ResponseEntity<Void> publishLive(@ModelAttribute LivePublishFormDTO form) {
        return liveService.publishLive(form.getName()) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/publish-done")
    public ResponseEntity<Void> publishLiveDone(@ModelAttribute LivePublishFormDTO form) {
        liveService.endLive(form.getName());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{liveId}")
    public ResponseEntity<Void> updateLive(){
        boolean result = liveService.updateLive();
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}