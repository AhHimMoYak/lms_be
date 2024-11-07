package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.course.dto.*;
import com.example.ahimmoyakbackend.course.service.CourseProvideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courseProvide")
public class CourseProvideController {
    private final CourseProvideService courseProvideService;

    @GetMapping("1")
    public ResponseEntity<CourseProvideDetailByCompanyResponseDto> getCourseDetailByCompany(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(courseProvideService.getCourseDetailByCompany(userDetails));
    }

    @GetMapping("2")
    public ResponseEntity<CourseProvideDetailByInstitutionResponseDto> getCourseDetailByInstitution(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(courseProvideService.getCourseDetailByInstitution(userDetails));
    }

    @GetMapping("3")
    public ResponseEntity<CourseProvideDetailByEmployeeResponseDto> getCourseDetailByEmployee(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(courseProvideService.getCourseDetailByEmployee(userDetails));
    }

    @GetMapping("4")
    public ResponseEntity<CourseProvideGetCourseListByInstitutionResponseDto> getCourseListByInstitution(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(courseProvideService.getCourseListByInstitution(userDetails));
    }

    @GetMapping("5")
    public ResponseEntity<CourseProvideGetCourseListByEmployeeResponseDto> getCourseListByEmployee(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(courseProvideService.getCourseListByEmployee(userDetails));
    }

}
