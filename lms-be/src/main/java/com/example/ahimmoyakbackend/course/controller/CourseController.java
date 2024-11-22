package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.dto.CourseCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.CourseDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseListResponseDto;
import com.example.ahimmoyakbackend.course.dto.EmployeeCourseListResponseDto;
import com.example.ahimmoyakbackend.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {
    private final CourseService courseService;

    // Fixme 코스 디테일 조회할때 수강중인 코스(코스프로바이드) 인경우 코스프로바이드의 정보도 같이 반환해야함, 계약을 위해 코스정보만 조회하는경우도 필요 이 두가지 경우 모두 고려해서 로직 변경및 api 추가해야함
    @GetMapping("/{courseId}/detail")
    public ResponseEntity<CourseDetailResponseDto> getCourseDetail(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getDetail(courseId));
    }

    @PostMapping
    public ResponseEntity<Long> createCourse(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CourseCreateRequestDto requestDto
    ) {
        return ResponseEntity.ok(courseService.create(userDetails, requestDto));
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<String> updateCourse(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long courseId,
            @RequestBody CourseCreateRequestDto requestDto
    ) {
        return courseService.update(userDetails, courseId, requestDto) ? ResponseEntity.ok("코스 수정 성공") : ResponseEntity.badRequest().body("코스 수정 실패");
    }
 
    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long courseId
    ) {
        return courseService.delete(userDetails, courseId) ? ResponseEntity.ok("코스 삭제 성고") : ResponseEntity.badRequest().body("코스 삭제 실패");
    }

    @GetMapping
    public ResponseEntity<List<CourseListResponseDto>> getCoursesListByInstitution(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(courseService.getListByInstitution(userDetails));
    }

    // Fixme 코스 리스트 조회할때 수강중인 코스(코스프로바이드) 인경우 코스프로바이드의 정보도 같이 반환해야함, 계약을 위해 코스정보만 조회하는경우도 필요 이 두가지 경우 모두 고려해서 로직 변경및 api 추가해야함
    @GetMapping("/all")
    public ResponseEntity<List<CourseListResponseDto>> getAllCoursesList() {
        return ResponseEntity.ok(courseService.getAllList());
    }

    @GetMapping(value = "/all", params = "page")
    public ResponseEntity<Page<CourseListResponseDto>> getAllCoursesList(Pageable pageable) {
        return ResponseEntity.ok(courseService.getAllList(pageable));
    }

    @GetMapping(value = "/all", params = "category")
    public ResponseEntity<List<CourseListResponseDto>> getAllCoursesList(CourseCategory category) {
        return ResponseEntity.ok(courseService.getAllList(category));
    }

    @GetMapping(value = "/all", params = {"page", "category"})
    public ResponseEntity<Page<CourseListResponseDto>> getAllCoursesList(Pageable pageable, CourseCategory category) {
        return ResponseEntity.ok(courseService.getAllList(pageable, category));
    }

    @GetMapping("/user")
    public ResponseEntity<List<EmployeeCourseListResponseDto>> getAllCoursesList(@RequestParam String userName) {
        return ResponseEntity.ok(courseService.getAllList(userName));
    }

}
