package click.ahimmoyak.institutionservice.institution.service;

import click.ahimmoyak.institutionservice.auth.common.UserRole;
import click.ahimmoyak.institutionservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.institutionservice.auth.entity.User;
import click.ahimmoyak.institutionservice.auth.repository.UserRepository;
import click.ahimmoyak.institutionservice.course.common.CourseProvideState;
import click.ahimmoyak.institutionservice.course.dto.CourseProvideDetailResponseDto;
import click.ahimmoyak.institutionservice.course.dto.CourseProvideDto;
import click.ahimmoyak.institutionservice.course.dto.CourseProvidesResponseDto;
import click.ahimmoyak.institutionservice.course.dto.EnrollmentInfoDto;
import click.ahimmoyak.institutionservice.course.entity.CourseProvide;
import click.ahimmoyak.institutionservice.course.entity.Enrollment;
import click.ahimmoyak.institutionservice.course.repository.CourseProvideRepository;
import click.ahimmoyak.institutionservice.course.repository.EnrollmentRepository;
import click.ahimmoyak.institutionservice.global.dto.MessageResponseDto;
import click.ahimmoyak.institutionservice.institution.repository.ManagerRepository;
import click.ahimmoyak.institutionservice.institution.dto.CourseProvideRequestDto;
import click.ahimmoyak.institutionservice.institution.dto.CreateInstitutionRequestDto;
import click.ahimmoyak.institutionservice.institution.dto.GetInstitutionDetailRequestDto;
import click.ahimmoyak.institutionservice.institution.dto.UpdateInstitutionRequestDto;
import click.ahimmoyak.institutionservice.institution.entity.Institution;
import click.ahimmoyak.institutionservice.institution.entity.Manager;
import click.ahimmoyak.institutionservice.institution.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
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
    public MessageResponseDto updateInstitution(UserDetailsImpl userDetails, UpdateInstitutionRequestDto requestDto, Long institutionId) {

        Manager manager = managerRepository.findByUser(userDetails.getUser());
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 교육기관입니다."));
        institution.patch(requestDto);

        return MessageResponseDto.builder().message("회사 수정 성공").build();
    }

    @Override
    public GetInstitutionDetailRequestDto getInstitutionDetail(UserDetailsImpl userDetails) {

        Institution institution = institutionRepository.findById(userDetails.getUser().getManager().getInstitution().getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 교육 기간입니다."));

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
    public CourseProvidesResponseDto getCourseProvideListByInstitution(UserDetailsImpl userDetails) {
        Institution institution = userDetails.getUser().getManager().getInstitution();

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
    public MessageResponseDto courseProvideResponse(UserDetailsImpl userDetails, Long courseProvideId, CourseProvideRequestDto requestDto) {

        CourseProvide courseProvide = courseProvideRepository.findById(courseProvideId).orElseThrow(() -> new IllegalArgumentException("계약 아이디가 없습니다."));


        if (requestDto.state().equals(CourseProvideState.ACCEPTED)) {
            courseProvide.accept();
            courseProvideRepository.save(courseProvide);
            return MessageResponseDto.builder()
                    .message("수락하셨습니다.")
                    .build();
        } else if (requestDto.state().equals(CourseProvideState.DECLINED)) {
            courseProvide.reject();
            courseProvideRepository.save(courseProvide);
            return MessageResponseDto.builder()
                    .message("거절하셨습니다.")
                    .build();
        } else {
            throw new IllegalArgumentException("유효하지 않은 상태 값입니다.");
        }
    }

    @Override
    public CourseProvideDetailResponseDto getCourseProvideDetailByInstitution(UserDetails userDetails, Long courseProvideId) {

        List<Enrollment> enrollments = enrollmentRepository.findAllByCourseProvide_Id(courseProvideId);
        CourseProvide courseProvide = courseProvideRepository.findById(courseProvideId).orElseThrow(() -> new IllegalArgumentException("계약 아이디가 없습니다."));
        List<EnrollmentInfoDto> enrollmentInfoDto = enrollments.stream().map(enrollment -> EnrollmentInfoDto.builder()
                        .username(enrollment.getUser().getUsername())
                        .state(enrollment.getState())
                        .build())
                .collect(Collectors.toList());

        return CourseProvideDetailResponseDto.builder()
                .beginDate(courseProvide.getBeginDate())
                .endDate(courseProvide.getEndDate())
                .attendeeCount(courseProvide.getAttendeeCount())
                .state(courseProvide.getState())
                .learnerList(enrollmentInfoDto)
                .build();

    }

    @Override
    public MessageResponseDto confirmEnrollments(UserDetails userDetails, Long courseProvideId) {

        CourseProvide courseProvide = courseProvideRepository.findById(courseProvideId)
                .orElseThrow(() -> new IllegalArgumentException("계약 아이디가 없습니다."));

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


    }
}

