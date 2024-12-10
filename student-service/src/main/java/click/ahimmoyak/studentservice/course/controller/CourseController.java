package click.ahimmoyak.studentservice.course.controller;

import click.ahimmoyak.studentservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.studentservice.course.dto.*;
import click.ahimmoyak.studentservice.course.service.CourseService;
import click.ahimmoyak.studentservice.course.common.CourseCategory;
import click.ahimmoyak.studentservice.global.dto.MessageResponseDto;
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
@RequestMapping("/v1/students/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{courseId}/details")
    public ResponseEntity<CourseDetailResponseDto> getCourseDetail(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getDetail(courseId));
    }

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

    @GetMapping("/curriculums/contents/{contentId}")// 콘텐츠 디테일 보기
    public ResponseEntity<ContentDetailResponseDto> getContentDetail(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String contentId) {
        courseService.createContentHistory(userDetails, contentId);
        return ResponseEntity.ok(courseService.getContentDetail(contentId));
    }

    @GetMapping("/{courseProvideId}/detail") // courseProvideId로 수강된 코스 보기
    public ResponseEntity<CourseDetailResponseDto> getDetail(@RequestParam Long userId, @PathVariable Long courseProvideId) {
        return ResponseEntity.ok(courseService.getDetail(userId, courseProvideId));
    }

    @GetMapping("/courseList") // 수강생의 courseList 조회
    public ResponseEntity<List<CourseListResponseDto>> getAllCourseList(@RequestParam Long userId) {
        return ResponseEntity.ok(courseService.getCourseList(userId));
    }

    @GetMapping("/{courseProvideId}/courseId")
    public ResponseEntity<CourseIdDto> getCourseIdByCourseProvideId(@PathVariable Long courseProvideId) {
        return ResponseEntity.ok(courseService.getCourseId(courseProvideId));
    }

}
