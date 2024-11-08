package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.course.dto.CourseProvideDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseProvidesResponseDto;
import com.example.ahimmoyakbackend.course.service.CourseProvideService;
import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentConfirmRequestDto;
import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentSubmitEmployeeListRequestDto;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courseProvide")
public class CourseProvideController {
    private final CourseProvideService courseProvideService;

    @GetMapping  // FIXME 회사, 교육기관, 사용자(employee) 합쳐서 작성
    public ResponseEntity<CourseProvideDetailResponseDto> getCourseProvideDetail(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long courseProvideId) {
        return ResponseEntity.ok(courseProvideService.getCourseProvideDetail(userDetails, courseProvideId));
    }

    @GetMapping(value = "/", params = "institutionId")
    public ResponseEntity<CourseProvidesResponseDto> getCourseProvideListByInstitution(@AuthenticationPrincipal UserDetails userDetails ,@RequestParam Long institutionId) {
        return ResponseEntity.ok(courseProvideService.getCourseProvideListByInstitution(userDetails));
    }

    @GetMapping(value = "/", params = "companyId")
    public ResponseEntity<CourseProvidesResponseDto> getCourseProvideListByCompany(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long companyId) {
        return ResponseEntity.ok(courseProvideService.getCourseProvideListByEmployee(userDetails));
    }
    // TODO 신청, 수락, enrollment 와 고민. 코스프로바이드에 대해서 보내는거

    @PostMapping("/{courseProvideId}/enroll")
    public ResponseEntity<MessageResponseDto> submitEmployeeListForEnrollment(@AuthenticationPrincipal UserDetails userDetails,
                                                                              @RequestBody EnrollmentSubmitEmployeeListRequestDto requestDto,
                                                                              @PathVariable String courseProvideId){
//        MessageResponseDto responseDto = ;

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PostMapping("/{courseProvideId}/confirm")
    public  ResponseEntity<MessageResponseDto> confirmEnrollments(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @RequestBody EnrollmentConfirmRequestDto requestDto){
//        MessageResponseDto responseDto =

        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
