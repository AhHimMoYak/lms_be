package click.ahimmoyak.companyservice.course.controller;

import click.ahimmoyak.companyservice.course.dto.CourseDetailResponseDto;
import click.ahimmoyak.companyservice.course.dto.CourseListResponseDto;
import click.ahimmoyak.companyservice.course.dto.EmployeeCourseListResponseDto;
import click.ahimmoyak.companyservice.course.common.CourseCategory;
import click.ahimmoyak.companyservice.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies/courses")
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

    @GetMapping("/users")
    public ResponseEntity<List<EmployeeCourseListResponseDto>> getAllCoursesList(@RequestParam String userName) {
        return ResponseEntity.ok(courseService.getAllList(userName));
    }

}
