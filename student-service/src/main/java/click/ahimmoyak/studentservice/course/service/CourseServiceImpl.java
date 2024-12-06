package click.ahimmoyak.studentservice.course.service;

import click.ahimmoyak.studentservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.studentservice.auth.entity.User;
import click.ahimmoyak.studentservice.auth.repository.UserRepository;
import click.ahimmoyak.studentservice.auth.service.UserService;
import click.ahimmoyak.studentservice.course.common.ContentsHistoryState;
import click.ahimmoyak.studentservice.course.common.CourseCategory;
import click.ahimmoyak.studentservice.course.common.CourseProvideState;
import click.ahimmoyak.studentservice.course.common.EnrollmentState;
import click.ahimmoyak.studentservice.course.dto.*;
import click.ahimmoyak.studentservice.course.entity.*;
import click.ahimmoyak.studentservice.course.repository.*;
import click.ahimmoyak.studentservice.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ContentsRepository contentsRepository;
    private final ContentsHistoryRepository contentsHistoryRepository;
    private final CourseProvideRepository courseProvideRepository;

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
    public CourseDetailResponseDto getDetail(long courseProvideId, UserDetails userDetails) {

        CourseProvide courseProvide = courseProvideRepository.findById(courseProvideId)
                .orElseThrow(() -> new IllegalArgumentException("해당 코스를 찾을 수 없습니다."));

        User user = userRepository.findById(((UserDetailsImpl) userDetails).getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByUserAndCourseProvide(user, courseProvide);
        if (enrollmentOptional.isEmpty()) {
            throw new IllegalArgumentException("해당 유저는 이 코스에 등록되어 있지 않습니다.");
        }

        List<CurriculumListResponseDto> curriculumList = courseProvide.getCourse().getCurriculumList()
                .stream()
                .map(curriculum -> CurriculumListResponseDto.builder()
                        .id(curriculum.getId())
                        .title(curriculum.getTitle())
                        .contentList(curriculum.getContentsList()
                                .stream()
                                .map(content -> ContentListResponseDto.builder()
                                        .id(content.getId())
                                        .type(content.getType())
                                        .title(content.getTitle())
                                        .build()
                                )
                                .collect(Collectors.toList())
                        )
                        .build()
                )
                .collect(Collectors.toList());

        return CourseDetailResponseDto.builder()
                .title(courseProvide.getCourse().getTitle())
                .introduction(courseProvide.getCourse().getIntroduction())
                .instructor(courseProvide.getInstitution().getName())
                .beginDate(courseProvide.getBeginDate())
                .endDate(courseProvide.getEndDate())
                .state(courseProvide.getCourse().getState())
                .category(courseProvide.getCourse().getCategory())
                .institutionId(courseProvide.getInstitution().getId())
                .curriculumList(curriculumList)
                .build();
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
    public ContentDetailResponseDto getContentDetail(String contentId) {
        Contents content = contentsRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("컨텐츠가 존재하지 않습니다."));

        return ContentDetailResponseDto.builder()
                .contentId(content.getId())
                .createdDate(content.getCreatedAt())
                .modifiedDate(content.getModifiedAt())
                .videoDuration(content.getVideoDuration())
                .idx(content.getIdx())
                .tittle(content.getTitle())
                .curriculumId(content.getCurriculum().getId())
                .build();
    }

    @Override
    public MessageResponseDto createContentHistory(UserDetailsImpl userDetails, String contentId) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(()->new IllegalArgumentException("유저가 없습니다."));

        Contents contents = contentsRepository.findById(contentId).orElseThrow(()->new IllegalArgumentException("콘텐츠가 없습니다."));

        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentsByUserAndContent(user, contentId);

        Enrollment enrollment = enrollments.getFirst();

        Optional<ContentsHistory> existingHistory = contentsHistoryRepository.findByEnrollmentAndContents(enrollment, contents);
        if (existingHistory.isPresent()) {
            return new MessageResponseDto("이미 만들어진 히스토리입니다.");
        }

        ContentsHistory contentsHistory = ContentsHistory.builder()
                .state(ContentsHistoryState.COMPLETED)
                .contents(contents)
                .enrollment(enrollment)
                .build();

        contentsHistoryRepository.save(contentsHistory);

        return MessageResponseDto.builder()
                .message("history 생성 완료")
                .build();
    }

    @Override
    public List<CourseListResponseDto> getCourseList(UserDetailsImpl userDetails) {

        User user = userService.getAuth(userDetails);

        List<CourseProvide> courseProvideList = courseProvideRepository.findByEnrollments_User(user);

        return courseProvideList.stream()
                .filter(courseProvide -> courseProvide.getState() == CourseProvideState.ONGOING)
                .filter(courseProvide -> courseProvide.getEnrollments().stream()
                        .anyMatch(enrollment -> enrollment.getUser().equals(user) &&
                                enrollment.getState() == EnrollmentState.AVAILABLE))
                .map(courseProvide -> {
                    List<String> contentIds = contentsRepository.findAllContentIdsByCourseIdAsString(
                            courseProvide.getCourse().getId()
                    );

                    Long completedContentCount = contentsHistoryRepository.countCompletedContentByUserIdAndContentIds(
                            user.getId(),
                            contentIds
                    );

                    long totalContentCount = contentIds.size();

                    return CourseListResponseDto.builder()
                            .id(courseProvide.getId())
                            .title(courseProvide.getCourse().getTitle())
                            .introduction(courseProvide.getCourse().getIntroduction())
                            .instructor(courseProvide.getCourse().getInstructor())
                            .category(courseProvide.getCourse().getCategory())
                            .totalContentCount(totalContentCount)
                            .completedContentCount(completedContentCount)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
