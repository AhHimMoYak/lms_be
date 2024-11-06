package com.example.ahimmoyakbackend.enrollment.controller;

import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentIdResponseDto;
import com.example.ahimmoyakbackend.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // Fixme 인롤먼트 생성시 코스가 아닌 코스프로바이드에 되도록 로직 변경해야함, 그러나 코스프로바이드 생성시 자동으로 생성되는것도 고려해야함
    @GetMapping("/{courseId}/enroll")
    public ResponseEntity<String> makeEnrollment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable long courseId
    ) {
        return enrollmentService.make(userDetails, courseId)
                ? ResponseEntity.ok("수강신청 성공")
                : ResponseEntity.badRequest().body("수강신청 실패");
    }

    /*
    아래 두 메소드는 모두 같은 동작을 함,
    enrollmentId 를 직접 알고 수강취소를 하는 경우와,
    코스에 대한 정보만 알고있는 경우 수강취소를 하는 경우의
    두 가지 경우를 모두 고려
     */
    @DeleteMapping("/{courseId}/enroll/{enrollId}")
    public ResponseEntity<String> cancelEnrollment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable long enrollId
    ) {
        return enrollmentService.cancel(userDetails, enrollId)
                ? ResponseEntity.ok("수강신청 취소 성공")
                : ResponseEntity.badRequest().body("수강신청 취소 실패");
    }

    @DeleteMapping("/{courseId}/enroll")
    public ResponseEntity<String> cancelEnrollment(
            @PathVariable long courseId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return enrollmentService.cancel(courseId, userDetails)
                ? ResponseEntity.ok("수강신청 취소 성공")
                : ResponseEntity.badRequest().body("수강신청 취소 실패");
    }

    @GetMapping("/{courseId}/enrollId")
    public ResponseEntity<EnrollmentIdResponseDto> getEnrollment(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @PathVariable("courseId") long courseId
    ) {

        EnrollmentIdResponseDto getEnrolled = enrollmentService.getEnrollId(userDetails, courseId);
        return ResponseEntity.status(HttpStatus.OK).body(getEnrolled);
    }
}
