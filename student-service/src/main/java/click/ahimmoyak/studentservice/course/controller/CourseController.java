package click.ahimmoyak.studentservice.course.controller;

import click.ahimmoyak.studentservice.course.dto.CourseDetailResponseDto;
import click.ahimmoyak.studentservice.course.dto.CourseListResponseDto;
import click.ahimmoyak.studentservice.course.dto.EmployeeCourseListResponseDto;
import click.ahimmoyak.studentservice.course.service.CourseService;
import click.ahimmoyak.studentservice.course.common.CourseCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
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

}
