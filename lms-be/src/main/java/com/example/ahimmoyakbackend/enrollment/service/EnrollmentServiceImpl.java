package com.example.ahimmoyakbackend.enrollment.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.course.repository.EnrollmentRepository;
import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentConfirmRequestDto;
import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentIdResponseDto;
import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentSubmitEmployeeListRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserService userService;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public boolean make(UserDetails userDetails, long courseId) {
        // Todo 인롤먼트 생성 로직 완전 변경해야함
//        User user = userService.getAuth(userDetails);
//        Course course = courseRepository.findById(courseId).orElse(null);
//        if (course == null) {
//            throw new ApiException(HttpStatus.NOT_FOUND, "코스를 찾을 수 없습니다.");
//        }
//        if (course.getBeginDate().isBefore(LocalDate.now())) {
//            throw new ApiException(HttpStatus.METHOD_NOT_ALLOWED, "이미 시작된 코스입니다.");
//        }
//        enrollmentRepository.save(Enrollment.builder()
//                .user(user)
//                .course(course)
//                .state(EnrollmentState.NOT_STARTED)
//                .build());
        return true;
    }

    @Override
    @Transactional
    public boolean cancel(UserDetails userDetails, long enrollId) {
        // Todo 인롤먼트 취소 로직 완전 변경해야함
//        User user = userService.getAuth(userDetails);
//        Enrollment enrollment = enrollmentRepository.findById(enrollId).orElse(null);
//        if (enrollment == null) {
//            throw new ApiException(HttpStatus.NOT_FOUND, "수강신청을 찾을 수 없습니다.");
//        }
//        if (enrollment.getCourse().getBeginDate().isBefore(LocalDate.now())) {
//            throw new ApiException(HttpStatus.METHOD_NOT_ALLOWED, "이미 시작된 코스입니다.");
//        }
//        enrollmentRepository.delete(enrollment);
        return true;
    }

    @Override
    @Transactional
    public boolean cancel(long courseId, UserDetails userDetails) {
        // Todo 인롤먼트 취소 로직 완전 변경해야함
//        User user = userService.getAuth(userDetails);
//        Course course = courseRepository.findById(courseId).orElse(null);
//        if (course == null) {
//            throw new ApiException(HttpStatus.NOT_FOUND, "코스를 찾을 수 없습니다.");
//        }
//        Enrollment enrollment = enrollmentRepository.findByUserAndCourse(user, course).orElse(null);
//        if (enrollment == null) {
//            throw new ApiException(HttpStatus.NOT_FOUND, "수강신청을 찾을 수 없습니다.");
//        }
//        if (course.getBeginDate().isBefore(LocalDate.now())) {
//            throw new ApiException(HttpStatus.METHOD_NOT_ALLOWED, "이미 시작된 코스입니다.");
//        }
//        enrollmentRepository.delete(enrollment);
        return true;
    }

    @Override
    @Transactional
    public EnrollmentIdResponseDto getEnrollId(UserDetails userDetails, Long courseId) {
        User user = userService.getAuth(userDetails);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("코스정보가 존재하지 않습니다 "));

//        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByUserIdAndCourseId(user.getId(), course.getId());

//        Long enrollmentId = enrollmentOptional.map(Enrollment::getId).orElse(null);

//        return EnrollmentIdResponseDto.builder()
//                .id(enrollmentId)
//                .build();

        return null;
    }

    @Override
    public String submitEmployeeListForEnrollment(UserDetails userDetails, EnrollmentSubmitEmployeeListRequestDto requestDto) {

        return "";
    }

    @Override
    public String confirmEnrollments(UserDetails userDetails, EnrollmentConfirmRequestDto requestDto) {

        return "";
    }
}
