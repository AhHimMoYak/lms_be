package com.example.ahimmoyakbackend.institution.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.course.dto.CourseProvideDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseProvidesResponseDto;
import com.example.ahimmoyakbackend.course.service.CourseProvideService;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.institution.dto.*;
import com.example.ahimmoyakbackend.institution.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/institution")
public class InstitutionController {

    private final InstitutionService institutionService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> createInstitution(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateInstitutionRequestDto requestDto
    ) {
        return ResponseEntity.ok(institutionService.createInstitution(userDetails, requestDto));
    }

    @GetMapping("/detail")
    public ResponseEntity<GetInstitutionDetailRequestDto> getInstitutionDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(institutionService.getInstitutionDetail(userDetails));
    }

    @GetMapping("/{institutionId}/detail")
    public ResponseEntity<GetInstitutionDetailRequestDto> getInstitutionDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long institutionId) {
        return ResponseEntity.ok(institutionService.getInstitutionDetail(userDetails, institutionId));
    }

    @PatchMapping("/{institutionId}")
    public ResponseEntity<MessageResponseDto> updateInstitution(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UpdateInstitutionRequestDto requestDto,
            @PathVariable Long institutionId) {
        return ResponseEntity.ok(institutionService.updateInstitution(userDetails, requestDto, institutionId));
    }

    @GetMapping
    public ResponseEntity<CourseProvidesResponseDto> getCourseProvideListByInstitution(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(institutionService.getCourseProvideListByInstitution(userDetails));
    }

    // 처음에 수강 신청 응답
    @PatchMapping("/{courseProvideId}/response")
    public ResponseEntity<MessageResponseDto> courseProvideResponse(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @PathVariable Long courseProvideId,
                                                                    @RequestBody CourseProvideRequestDto requestDto) {

        return ResponseEntity.ok(institutionService.courseProvideResponse(userDetails, courseProvideId, requestDto));
    }

    @GetMapping("/{courseProvideId}/courseProvideDetail")
    public ResponseEntity<CourseProvideDetailResponseDto> getCourseProvideDetailByInstitution(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long courseProvideId) {
        return ResponseEntity.ok(institutionService.getCourseProvideDetailByInstitution(userDetails, courseProvideId));
    }

    @PatchMapping("/{courseProvideId}/registration")// 수강등록
    public ResponseEntity<MessageResponseDto> confirmEnrollments(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @PathVariable Long courseProvideId) {

        return ResponseEntity.ok(institutionService.confirmEnrollments(userDetails, courseProvideId));
    }

}
