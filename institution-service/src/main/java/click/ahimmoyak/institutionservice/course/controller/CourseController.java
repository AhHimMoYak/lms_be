package click.ahimmoyak.institutionservice.course.controller;

import click.ahimmoyak.institutionservice.course.common.CourseCategory;
import click.ahimmoyak.institutionservice.course.dto.*;
import click.ahimmoyak.institutionservice.course.service.CourseService;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/institutions/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{courseId}/details")
    public ResponseEntity<CourseDetailResponseDto> getCourseDetail(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getDetail(courseId));
    }

    @PostMapping
    public ResponseEntity<CourseCreateResponseDto> createCourse(
            @RequestParam Long userId,
            @RequestBody CourseCreateRequestDto requestDto
    ) {
        return ResponseEntity.ok(courseService.create(userId, requestDto));
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<String> updateCourse(
            @PathVariable Long courseId,
            @RequestBody CourseUpdateRequestDto requestDto
    ) {
        log.info("코스 업데이트" + requestDto);
        return courseService.update(courseId, requestDto) ? ResponseEntity.ok("코스 수정 성공") : ResponseEntity.badRequest().body("코스 수정 실패");
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long courseId
    ) {
        return courseService.delete(userDetails, courseId) ? ResponseEntity.ok("코스 삭제 성공") : ResponseEntity.badRequest().body("코스 삭제 실패");
    }

    @GetMapping
    public ResponseEntity<List<CourseListResponseDto>> getCoursesListByInstitution(@RequestParam Long userId) {
        return ResponseEntity.ok(courseService.getListByInstitution(userId));
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

    @PutMapping("/{courseId}/curriculums/{curriculumId}/save")
    public ResponseEntity<MessageResponseDto> saveContents(@PathVariable String courseId,
                                                           @PathVariable Long curriculumId,
                                                           @RequestBody List<GetContentsRequestDto> requestDtos) {
        return ResponseEntity.ok(courseService.saveContents(curriculumId, requestDtos));
    }


    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponseDto>> getCategoryList() {
        return ResponseEntity.ok(Arrays.stream(CourseCategory.values()).map(CategoryResponseDto::from).toList());
    }

}
