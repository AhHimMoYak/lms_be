package click.ahimmoyak.studentservice.institution.service;

import click.ahimmoyak.studentservice.auth.common.UserRole;
import click.ahimmoyak.studentservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.studentservice.auth.entity.User;
import click.ahimmoyak.studentservice.auth.repository.UserRepository;
import click.ahimmoyak.studentservice.course.repository.CourseProvideRepository;
import click.ahimmoyak.studentservice.course.repository.EnrollmentRepository;
import click.ahimmoyak.studentservice.global.dto.MessageResponseDto;
import click.ahimmoyak.studentservice.global.exception.ApiException;
import click.ahimmoyak.studentservice.institution.entity.Institution;
import click.ahimmoyak.studentservice.institution.entity.Manager;
import click.ahimmoyak.studentservice.institution.dto.CreateInstitutionRequestDto;
import click.ahimmoyak.studentservice.institution.dto.GetInstitutionDetailRequestDto;
import click.ahimmoyak.studentservice.institution.repository.InstitutionRepository;
import click.ahimmoyak.studentservice.institution.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final ManagerRepository managerRepository;

    @Override
    public MessageResponseDto createInstitution(Long userId, CreateInstitutionRequestDto requestDto) {

        User user = userRepository.findById(userId).orElseThrow(()-> new ApiException(HttpStatus.UNAUTHORIZED, "유저가 존재하지 않습니다."));


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

}

