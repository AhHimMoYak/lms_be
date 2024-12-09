package click.ahimmoyak.institutionservice.institution.service;

import click.ahimmoyak.institutionservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.institutionservice.auth.entity.User;
import click.ahimmoyak.institutionservice.auth.repository.UserRepository;
import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import click.ahimmoyak.institutionservice.course.dto.*;
import click.ahimmoyak.institutionservice.course.entity.Course;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import click.ahimmoyak.institutionservice.course.entity.Enrollment;
import click.ahimmoyak.institutionservice.course.repository.CourseProvideRepository;
import click.ahimmoyak.institutionservice.course.repository.CourseRepository;
import click.ahimmoyak.institutionservice.course.repository.EnrollmentRepository;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import click.ahimmoyak.institutionservice.global.exception.ApiException;
import click.ahimmoyak.institutionservice.institution.dto.*;
import click.ahimmoyak.institutionservice.institution.repository.ManagerRepository;
import click.ahimmoyak.institutionservice.institution.entity.Institution;
import click.ahimmoyak.institutionservice.institution.entity.Manager;
import click.ahimmoyak.institutionservice.institution.repository.InstitutionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final ManagerRepository managerRepository;
    private final CourseProvideRepository courseProvideRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Override
    public MessageResponseDto createInstitution(UserDetailsImpl userDetails, CreateInstitutionRequestDto requestDto) {

        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new IllegalArgumentException("존재하지않는 user 입니다"));

        if (institutionRepository.existsByBusinessNumber(requestDto.businessNumber())) {
            throw new IllegalArgumentException("이미 존재하는 법인번호 입니다");
        }

        if (institutionRepository.existsByCertifiedNumber(requestDto.certifiedNumber())) {
            throw new IllegalArgumentException("이미 존재하는 관리번호 입니다");
        }

        Institution institution = Institution.builder()
                .name(requestDto.name())
                .ownerName(requestDto.ownerName())
                .businessNumber(requestDto.businessNumber())
                .certifiedNumber(requestDto.certifiedNumber())
                .email(requestDto.email())
                .phone(requestDto.phone())
                .build();
        institutionRepository.save(institution);

        Manager manager = Manager.builder()
                .user(user)
                .institution(institution)
                .build();

        managerRepository.save(manager);

        return MessageResponseDto.builder().message("회사 생성 성공").build();
    }

    @Override
    public MessageResponseDto updateInstitution(Long userId, UpdateInstitutionRequestDto requestDto) {

        Institution institution = userRepository.findById(userId).orElseThrow(()->new ApiException(HttpStatus.UNAUTHORIZED,"존재 하지 않은 유저입니다.")).getManager().getInstitution();
        institution.patch(requestDto);

        institutionRepository.save(institution);

        return MessageResponseDto.builder().message("회사 수정 성공").build();
    }

    @Override
    public GetInstitutionDetailRequestDto getInstitutionDetail(Long userId) {

        Institution institution= userRepository.findById(userId).orElseThrow(()->new ApiException(HttpStatus.UNAUTHORIZED, "존재하지 않은 유저입니다.")).getManager().getInstitution();

        return GetInstitutionDetailRequestDto.builder()
                .id(institution.getId())
                .institutionName(institution.getName())
                .ownerName(institution.getOwnerName())
                .businessNumber(institution.getBusinessNumber())
                .certifiedNumber(institution.getCertifiedNumber())
                .email(institution.getEmail())
                .phone(institution.getPhone())
                .address(institution.getAddress())
                .description(institution.getDescription())
                .webSite(institution.getWebSite())
                .build();

    }

    @Override
    public GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails, Long institutionId) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 userId 입니다"));

        Institution institution = institutionRepository.findById(institutionId).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 교육기관입니다."));

        return GetInstitutionDetailRequestDto.builder()
                .id(institution.getId())
                .institutionName(institution.getName())
                .ownerName(institution.getOwnerName())
                .businessNumber(institution.getBusinessNumber())
                .certifiedNumber(institution.getCertifiedNumber())
                .email(institution.getEmail())
                .phone(institution.getPhone())
                .build();
    }

    @Override
    public CourseProvidesResponseDto getCourseProvideListByInstitution(Long userId) {
        Institution institution = userRepository.findById(userId).orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "매니저만 할수있습니다."))
                .getManager().getInstitution();

        List<CourseProvide> courseProvides = courseProvideRepository.findAllByInstitution(institution);

        List<CourseProvideDto> courseProvideDtoList = courseProvides.stream()
                .map(courseProvide -> CourseProvideDto.from(
                        courseProvide,
                        courseProvide.getCourse(),
                        courseProvide.getCompany(),
                        courseProvide.getInstitution()
                ))
                .collect(Collectors.toList());

        return CourseProvidesResponseDto.from(courseProvideDtoList);
    }

    @Override
    public MessageResponseDto courseProvideResponse(Long userId, Long courseProvideId, CourseProvideRequestDto requestDto) {

        CourseProvide courseProvide = courseProvideRepository.findById(courseProvideId).orElseThrow(() -> new IllegalArgumentException("계약 아이디가 없습니다."));

        switch (requestDto.action().toUpperCase()) {
            case "ACCEPT":
                courseProvide.accept();
                courseProvideRepository.save(courseProvide);
                return MessageResponseDto.builder().message("수락하셨습니다.").build();
            case "REJECT":
                courseProvide.reject();
                courseProvideRepository.save(courseProvide);
                return MessageResponseDto.builder().message("거절하셨습니다.").build();
            default:
                throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }
    }

    @Override
    public CourseProvideDetailResponseDto getCourseProvideDetailByInstitution(Long userId, Long courseProvideId) {

        List<Enrollment> enrollments = enrollmentRepository.findAllByCourseProvide_Id(courseProvideId);
        CourseProvide courseProvide = courseProvideRepository.findById(courseProvideId).orElseThrow(() -> new IllegalArgumentException("계약 아이디가 없습니다."));
        List<EnrollmentInfoDto> enrollmentInfoDto = enrollments.stream().map(enrollment -> EnrollmentInfoDto.builder()
                        .enrollmentId(enrollment.getId())
                        .username(enrollment.getUser().getUsername())
                        .build())
                .collect(Collectors.toList());

        return CourseProvideDetailResponseDto.builder()
                .courseProvideId(courseProvide.getId())
                .courseTitle(courseProvide.getCourse().getTitle())
                .createdDate(courseProvide.getCreatedAt())
                .period(courseProvide.getCourse().getPeriod())
                .instructor(courseProvide.getCourse().getInstructor())
                .companyName(courseProvide.getCompany().getName())
                .institutionName(courseProvide.getInstitution().getName())
                .email(courseProvide.getInstitution().getEmail())
                .phone(courseProvide.getInstitution().getPhone())
                .beginDate(courseProvide.getBeginDate())
                .createdDate(courseProvide.getCreatedAt())
                .endDate(courseProvide.getEndDate())
                .attendeeCount(courseProvide.getAttendeeCount())
                .state(courseProvide.getState())
                .learnerList(enrollmentInfoDto)
                .build();

    }

    @Override
    public MessageResponseDto confirmEnrollments(Long userId, Long courseProvideId, EnrollmentRequestDto requestDto) {

        CourseProvide courseProvide = courseProvideRepository.findById(courseProvideId)
                .orElseThrow(() -> new IllegalArgumentException("계약 아이디가 없습니다."));


        switch (requestDto.action().toUpperCase()) {
            case "APPROVE" :
            courseProvide.setState();
            List<Enrollment> enrollments = courseProvide.getEnrollments();
            for (Enrollment enrollment : enrollments) {
                enrollment.setState();
            }
            courseProvideRepository.save(courseProvide);
            enrollmentRepository.saveAll(enrollments);

            return MessageResponseDto.builder()
                    .message("강의 등록 되었습니다.")
                    .build();

            case "DENY":
            courseProvide.reject(); // 거절 상태로 설정
            List<Enrollment> enrollments1 = courseProvide.getEnrollments();
            courseProvideRepository.save(courseProvide);
            enrollmentRepository.saveAll(enrollments1);

            return MessageResponseDto.builder()
                    .message("강의 등록이 거절되었습니다.")
                    .build();
            default:
                throw new IllegalArgumentException("유효하지 않은 action 값입니다: " + requestDto.action());
        }

    }


    @Override
    public StartCourseProvideDetailResponseDto getStartCourseProvideDetailByInstitution(Long userId, Long courseProvideId) {

        List<Enrollment> enrollments = enrollmentRepository.findAllByCourseProvide_Id(courseProvideId);

        CourseProvide courseProvide = courseProvideRepository.findById(courseProvideId).orElseThrow(() -> new IllegalArgumentException("계약 아이디가 없습니다."));

        List<EnrollmentDto> enrollmentList = enrollments.stream().map(enrollment -> EnrollmentDto.builder()
                        .enrollmentId(enrollment.getId())
                        .enrollmentName(enrollment.getUser().getUsername())
                        .progress(enrollment.getProgress())
                        .build())
                .collect(Collectors.toList());

        return StartCourseProvideDetailResponseDto.builder()
                .courseTitle(courseProvide.getCourse().getTitle())
                .companyName(courseProvide.getCompany().getName())
                .state(courseProvide.getState())
                .beginDate(courseProvide.getBeginDate())
                .endDate(courseProvide.getProvisionPeriod())
                .learnerList(enrollmentList)
                .build();
    }

    @Override
    public GetDashboardResponseDto getDashboard(Long userId) {

        Institution institution = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."))
                .getManager().getInstitution();

        long totalCourses = courseRepository.countByInstitution(institution);

        long totalCourseProvides = courseProvideRepository.countByInstitution(institution);

        long totalStudents = enrollmentRepository.countCoursesByInstitution(institution);

        long newContractRequests = courseProvideRepository.countByInstitutionAndState(institution, CourseProvideState.ONGOING);

        List<GetProgressCourseProvideListDto> courseStatusList = courseRepository.findByInstitution(institution)
                .stream()
                .map(course -> {
                    long providingCompanies = courseProvideRepository.countDistinctCompaniesByCourse(course);
                    return GetProgressCourseProvideListDto.builder()
                            .courseTitle(course.getTitle())
                            .period(course.getPeriod())
                            .companyProvided(providingCompanies)
                            .build();
                })
                .collect(Collectors.toList());

        return GetDashboardResponseDto.builder()
                .totalCourse(totalCourses)
                .totalCourseProvide(totalCourseProvides)
                .totalEnrollment(totalStudents)
                .totalProgressCourseProvide(newContractRequests)
                .progressCourseProvideList(courseStatusList)
                .build();
    }
}

