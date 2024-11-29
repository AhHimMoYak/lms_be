package click.ahimmoyak.companyservice.course.service;

import click.ahimmoyak.companyservice.auth.service.UserService;
import click.ahimmoyak.companyservice.course.common.CourseCategory;
import click.ahimmoyak.companyservice.course.dto.*;
import click.ahimmoyak.companyservice.course.entity.Enrollment;
import click.ahimmoyak.companyservice.course.repository.CourseRepository;
import click.ahimmoyak.companyservice.course.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final UserService userService;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public CourseDetailResponseDto getDetail(long id) {
        return courseRepository.findById(id)
                .map(course -> CourseDetailResponseDto
                        .from(course, course.getCurriculumList().stream()
                                .map(curriculum -> CurriculumListResponseDto
                                        .from(curriculum, curriculum.getContentsList().stream()
                                                .map(ContentListResponseDto::from
                                                ).toList())
                                ).toList())).orElse(null);
    }

    @Override
    public List<CourseListResponseDto> getAllList() {
        return courseRepository.findAllOrderByState().stream()
                .map(CourseListResponseDto::from)
                .toList();
    }

    @Override
    public Page<CourseListResponseDto> getAllList(Pageable pageable) {
        return courseRepository.findAllOrderByState(pageable)
                .map(CourseListResponseDto::from);
    }

    @Override
    public List<CourseListResponseDto> getAllList(CourseCategory category) {
        return courseRepository.findAllByCategoryOrderByState(category).stream()
                .map(CourseListResponseDto::from)
                .toList();
    }

    @Override
    public Page<CourseListResponseDto> getAllList(Pageable pageable, CourseCategory category) {
        return courseRepository.findAllByCategoryOrderByState(category, pageable)
                .map(CourseListResponseDto::from);
    }

    @Override
    public List<EmployeeCourseListResponseDto> getAllList(String userName) {

        List<Enrollment> enrollments = enrollmentRepository.findByUser_Name(userName);

        return enrollments.stream()
                .filter(Objects::nonNull)
                .map(enrollment -> EmployeeCourseListResponseDto.from(enrollment.getCourseProvide().getCourse(), enrollment.getCourseProvide()))
                .collect(Collectors.toList());
    }
}
