package click.ahimmoyak.institutionservice.course.service;

import click.ahimmoyak.institutionservice.auth.entity.User;
import click.ahimmoyak.institutionservice.auth.repository.UserRepository;
import click.ahimmoyak.institutionservice.auth.service.UserService;
import click.ahimmoyak.institutionservice.course.common.CourseCategory;
import click.ahimmoyak.institutionservice.course.common.CourseState;
import click.ahimmoyak.institutionservice.course.dto.*;
import click.ahimmoyak.institutionservice.course.entity.Contents;
import click.ahimmoyak.institutionservice.course.entity.Course;
import click.ahimmoyak.institutionservice.course.entity.Curriculum;
import click.ahimmoyak.institutionservice.course.entity.Enrollment;
import click.ahimmoyak.institutionservice.course.repository.ContentsRepository;
import click.ahimmoyak.institutionservice.course.repository.CourseRepository;
import click.ahimmoyak.institutionservice.course.repository.CurriculumRepository;
import click.ahimmoyak.institutionservice.course.repository.EnrollmentRepository;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import click.ahimmoyak.institutionservice.global.exception.ApiException;
import click.ahimmoyak.institutionservice.institution.entity.Institution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final UserService userService;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ContentsRepository contentsRepository;
    private final CurriculumRepository curriculumRepository;
    private final UserRepository userRepository;

    @Override
    public CourseDetailResponseDto getDetail(long id) {
        return courseRepository.findById(id)
                .map(course -> CourseDetailResponseDto
                        .from(course, course.getCurriculumList().stream()
                                        .map(curriculum -> CurriculumListResponseDto
                                                .from(curriculum, curriculum.getContentsList().stream()
                                                        .map(ContentListResponseDto::from
                                                        ).toList())).toList(),
                                course.getCourseProvides().stream()
                                        .map(CourseProvideListDto::from).toList())).orElse(null);
    }

    @Override
    @Transactional
    public CourseCreateResponseDto create(Long userId, CourseCreateRequestDto requestDto) {
        Institution institution = userRepository.findById(userId).orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "유저가 없습니다. ")).getManager().getInstitution();

        Course course = Course.builder()
                .title(requestDto.title())
                .instructor(requestDto.instructor())
                .period(requestDto.period())
                .introduction(requestDto.introduction())
                .category(requestDto.category())
                .institution(institution)
                .state(CourseState.AVAILABLE)
                .build();
        Course saved = courseRepository.save(course);

        return CourseCreateResponseDto.from(saved.getId());
    }

    @Override
    @Transactional
    public boolean update(long id, CourseUpdateRequestDto requestDto) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NO_CONTENT, "없는 코스입니다."));
        courseRepository.save(course.patch(requestDto));
        return true;
    }

    @Override
    @Transactional
    public boolean delete(UserDetails userDetails, long id) {
        Course course = courseRepository.findById(id).orElse(null);
        // Todo 코스 삭제시 교육기관 매니저만 삭제가능하도록 권한 확인 해야함 (아래 주석 참고) -> 수정 완료
        if (course == null || !course.getInstitution().getId().equals(userService.getAuth(userDetails).getId())) {
            return false;
        }
        courseRepository.save(course.setState(CourseState.REMOVED));
        return true;
    }

    // 교육기관 매니저가 교육기관 코스들의 목록 조회
    @Override
    public List<CourseListResponseDto> getListByInstitution(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "유저 없음"));
        Long institutionId = user.getManager().getInstitution().getId();

        List<Course> courseList = courseRepository.findByInstitution_Id(institutionId);


        return courseList.stream()
                .map(CourseListResponseDto::from).collect(Collectors.toList());
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

        List<Enrollment> enrollments = enrollmentRepository.findByUser_Username(userName);

        return enrollments.stream()
                .filter(Objects::nonNull)
                .map(enrollment -> EmployeeCourseListResponseDto.from(enrollment.getCourseProvide().getCourse(), enrollment.getCourseProvide()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MessageResponseDto saveContents(Long curriculumId, List<GetContentsRequestDto> requestDtos) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new RuntimeException("Curriculum not found"));

        List<Contents> existingContents = contentsRepository.findAllByCurriculumId(curriculumId);

        List<String> requestContentIds = requestDtos.stream()
                .map(GetContentsRequestDto::contentId)
                .toList();

        existingContents.stream()
                .filter(content -> !requestContentIds.contains(content.getId()))
                .forEach(contentsRepository::delete);

        requestDtos.forEach(dto -> {
            Contents existingContent = contentsRepository.findById(dto.contentId())
                    .orElse(null);

            if (existingContent != null) {
                existingContent.patch(dto, curriculum);
                contentsRepository.save(existingContent);
            } else {
                Contents newContent = Contents.builder()
                        .id(dto.contentId())
                        .title(dto.contentTitle())
                        .type(dto.contentType())
                        .idx(dto.idx())
                        .videoDuration(dto.videoDuration())
                        .curriculum(curriculum)
                        .s3Url(dto.s3Url())
                        .build();
                contentsRepository.save(newContent);
            }
        });

        return new MessageResponseDto("콘텐츠 저장 완료");
    }


}
