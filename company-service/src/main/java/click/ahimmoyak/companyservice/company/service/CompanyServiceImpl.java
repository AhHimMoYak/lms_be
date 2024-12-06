package click.ahimmoyak.companyservice.company.service;

import click.ahimmoyak.companyservice.auth.common.UserRole;
import click.ahimmoyak.companyservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.companyservice.auth.entity.User;
import click.ahimmoyak.companyservice.auth.repository.UserRepository;
import click.ahimmoyak.companyservice.auth.service.UserService;
import click.ahimmoyak.companyservice.company.dto.*;
import click.ahimmoyak.companyservice.company.entity.Affiliation;
import click.ahimmoyak.companyservice.company.entity.Company;
import click.ahimmoyak.companyservice.company.repository.AffiliationRepository;
import click.ahimmoyak.companyservice.company.repository.CompanyRepository;
import click.ahimmoyak.companyservice.course.common.CourseProvideState;
import click.ahimmoyak.companyservice.course.common.EnrollmentState;
import click.ahimmoyak.companyservice.course.entity.Course;
import click.ahimmoyak.companyservice.course.entity.CourseProvide;
import click.ahimmoyak.companyservice.course.entity.Enrollment;
import click.ahimmoyak.companyservice.course.repository.CourseProvideRepository;
import click.ahimmoyak.companyservice.course.repository.CourseRepository;
import click.ahimmoyak.companyservice.course.repository.EnrollmentRepository;
import click.ahimmoyak.companyservice.global.dto.MessageResponseDto;
import click.ahimmoyak.companyservice.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final UserService userService;
    private final CompanyRepository companyRepository;
    private final AffiliationRepository affiliationRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final CourseProvideRepository courseProvideRepository;

    @Override
    @Transactional(readOnly = true)
    public CompanyDetailResponseDto getCompany(UserDetailsImpl userDetails) {
        User user = userService.getAuth(userDetails);
        Company company = companyRepository.findById(user.getAffiliation().getCompany().getId()).orElseThrow(
                ()-> new ApiException(HttpStatus.NOT_FOUND,"유저의 회사를 찾을수 없습니다."));

        return CompanyDetailResponseDto.builder().build().of(company);
    }

    @Override
    @Transactional
    public MessageResponseDto updateCompany(UserDetailsImpl userDetails, String name, UpdateCompanyRequestDto requestDto) {
        User user = userService.getAuth(userDetails);
        Company company = companyRepository.findByName(name).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "해당 회사를 찾을 수 없습니다."));
        Company usercompany = user.getAffiliation().getCompany();
        if (company != usercompany) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "해당 회사의 SUPERVISOR 가 아닙니다.");
        }
        company.patch(requestDto);
        companyRepository.save(company);

        return MessageResponseDto.builder()
                .message("회사 수정 완료")
                .build();
    }

    @Override
    @Transactional
    public MessageResponseDto disconnectCompany(UserDetailsImpl userDetails) {
        User user = userService.getAuth(userDetails);

        if (user.getAffiliation() == null) {
            throw new ApiException(HttpStatus.CONFLICT, "Affiliation 이 존재하지 않습니다.");
        }

        if(enrollmentRepository.existsById(user.getId())) {
            Enrollment enrollment = enrollmentRepository.findById(user.getId()).orElseThrow(null);
            if(enrollment.getCourseProvide().getCompany().getId().equals(user.getAffiliation().getCompany().getId())) {
                enrollmentRepository.delete(enrollment);
            }
        }
        affiliationRepository.delete(user.getAffiliation());

        userRepository.save(user);

        return MessageResponseDto.builder()
                .message("회사 탈퇴 완료")
                .build();
    }

    @Override
    @Transactional
    public MessageResponseDto deleteAffiliation(UserDetailsImpl userDetails, String username) {
        User supervisor = userService.getAuth(userDetails);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "해당 사원이 존재하지 않습니다.")
        );

        Affiliation affiliation = affiliationRepository.findByUserId(user.getId()).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "해당 계약이 존재하지 않습니다.")
        );

        if (enrollmentRepository.existsById(user.getId())) {
            Enrollment enrollment = enrollmentRepository.findById(user.getId()).orElseThrow(null);
            if (enrollment.getCourseProvide().getCompany().getId().equals(user.getAffiliation().getCompany().getId())) {
                enrollmentRepository.delete(enrollment);
            }
        }

        if (supervisor.getAffiliation().getCompany() != user.getAffiliation().getCompany()) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "해당 회사의 SUPERVISOR 가 아닙니다.");
        }


        affiliationRepository.delete(affiliation);

        return MessageResponseDto.builder()
                .message("사원 삭제 완료")
                .build();
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<GetEmployeeListResponseDto> getEmployeeList(UserDetailsImpl userDetails) {
//        User supervisor = userService.getAuth(userDetails);
//        Long companyId = supervisor.getAffiliation().getCompany().getId();
//
//        if(companyId == null) {
//            throw new ApiException(HttpStatus.NOT_FOUND, "해당 supervisor 의 companyId가 존재하지 않습니다");
//        }
//
//        List<Affiliation> employees = affiliationRepository.findByCompany_Id(companyId);
//
//        return employees.stream()
//                .map(GetEmployeeListResponseDto::from)
//                .toList();
//    }

    @Override
    @Transactional
    public MessageResponseDto createCourseProvider(UserDetailsImpl userDetails, Long courseId, CreateCourseProvideRequestDto requestDto) {
        User supervisor = userService.getAuth(userDetails);
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "해당 Course 가 존재하지 않습니다.")
        );

        CourseProvide courseProvide = CourseProvide.builder()
                .company(supervisor.getAffiliation().getCompany())
                .beginDate(requestDto.beginDate())
                .endDate(requestDto.endDate())
                .state(CourseProvideState.PENDING)
                .attendeeCount(requestDto.attendeeCount())
                .deposit(requestDto.deposit())
                .institution(course.getInstitution())
                .course(course)
                .enrollments(null)
                .build();

        courseProvideRepository.save(courseProvide);

        return MessageResponseDto.builder()
                .message("courseProvide 생성 완료")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseProvideListResponseDto> getCourseProvideList(UserDetailsImpl userDetails) {
        User user = userService.getAuth(userDetails);
        List<CourseProvide> courseProvideList = courseProvideRepository.findByCompany_Id(user.getAffiliation().getCompany().getId());


        return courseProvideList.stream()
                .filter(Objects::nonNull)
                .map(courseProvide -> CourseProvideListResponseDto.from(courseProvide, user.getAffiliation().getCompany(), courseProvide.getInstitution())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MessageResponseDto submitEmployeeListForEnrollment(UserDetailsImpl userDetails, submitEmployeeListRequestDto requestDto) {
        User supervisor = userService.getAuth(userDetails);

        List<User> userList = userRepository.findAllByUsernameIn(requestDto.employeeUserName());

        CourseProvide courseProvide = courseProvideRepository.findById(requestDto.courseProvideId()).orElseThrow(
                ()-> new ApiException(HttpStatus.NOT_FOUND,"해당 courseProvideId 가 존재하지 않습니다."));

        if(!courseProvide.getState().equals(CourseProvideState.ACCEPTED)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "코스가 수강신청 할 상태가 아닙니다.");
        }

        int attendeeCount = courseProvide.getAttendeeCount();
        int selectedEmployeeCount = userList.size();

        if (selectedEmployeeCount > attendeeCount) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "선택된 사원 수가 수강신청 가능 인원보다 많습니다.");
        }

        for (User selected : userList) {
            boolean enrollmentExists = enrollmentRepository.existsByUserAndCourseProvide(selected, courseProvide);
            if (enrollmentExists) {
                throw new ApiException(HttpStatus.CONFLICT, "이미 수강신청된 사원이 있습니다 " );
            }

            Enrollment enrollment = Enrollment.builder()
                    .state(EnrollmentState.UNAVAILABLE)
                    .certificateDate(LocalDateTime.now())
                    .courseProvide(courseProvide)
                    .user(selected)
                    .build();
            enrollmentRepository.save(enrollment);
        }

        courseProvide.updateCourseProvideState(CourseProvideState.ATTENDEE_PENDING);
        courseProvideRepository.save(courseProvide);

        return MessageResponseDto.builder()
                .message("선택한 사원들을 교육기관에 넘기기 완료")
                .build();
    }

}
