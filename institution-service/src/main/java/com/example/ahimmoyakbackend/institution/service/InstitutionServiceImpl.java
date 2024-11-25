package com.example.ahimmoyakbackend.institution.service;

import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.course.common.CourseProvideState;
import com.example.ahimmoyakbackend.course.dto.CourseProvideDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseProvideDto;
import com.example.ahimmoyakbackend.course.dto.CourseProvidesResponseDto;
import com.example.ahimmoyakbackend.course.dto.EnrollmentInfoDto;
import com.example.ahimmoyakbackend.course.entity.CourseProvide;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.course.repository.CourseProvideRepository;
import com.example.ahimmoyakbackend.course.repository.EnrollmentRepository;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.institution.dto.CourseProvideRequestDto;
import com.example.ahimmoyakbackend.institution.dto.CreateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.dto.GetInstitutionDetailRequestDto;
import com.example.ahimmoyakbackend.institution.dto.UpdateInstitutionRequestDto;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import com.example.ahimmoyakbackend.institution.repository.InstitutionRepository;
import com.example.ahimmoyakbackend.institution.repository.ManagerRepository;
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

        if ((user.getRole().equals(UserRole.MANAGER))){
            throw new IllegalArgumentException("이미 교육기관에 소속되어있습니다.");
        }

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

        user.patch();
        managerRepository.save(manager);

        return MessageResponseDto.builder().message("회사 생성 성공").build();
    }

    @Override
    public MessageResponseDto updateInstitution(UserDetailsImpl userDetails, UpdateInstitutionRequestDto requestDto, Long institutionId) {

        Manager manager = managerRepository.findByUser(userDetails.getUser());
        if (!userDetails.getUser().getRole().equals(UserRole.MANAGER)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
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
                        .enrollmentId(enrollment.getId())
                        .username(enrollment.getUser().getUsername())
                        .name(enrollment.getUser().getName())
                        .email(enrollment.getUser().getEmail())
                        .birth(enrollment.getUser().getBirth())
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
            enrollment.setState();  // 원하는 상태로 변경
        }

        courseProvideRepository.save(courseProvide);
        enrollmentRepository.saveAll(enrollments);

        return MessageResponseDto.builder()
                .message("강의 등록 되었습니다.")
                .build();


    }
}

