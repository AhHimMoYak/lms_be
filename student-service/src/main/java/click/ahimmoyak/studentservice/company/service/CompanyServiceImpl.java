package click.ahimmoyak.studentservice.company.service;

import click.ahimmoyak.studentservice.auth.common.UserRole;
import click.ahimmoyak.studentservice.auth.config.security.UserDetailsImpl;
import click.ahimmoyak.studentservice.auth.entity.User;
import click.ahimmoyak.studentservice.auth.repository.UserRepository;
import click.ahimmoyak.studentservice.auth.service.UserService;
import click.ahimmoyak.studentservice.company.dto.CheckCompanyResponseDto;
import click.ahimmoyak.studentservice.company.dto.CompanyDetailResponseDto;
import click.ahimmoyak.studentservice.company.dto.CreateCompanyRequestDto;
import click.ahimmoyak.studentservice.company.dto.SearchCompanyResponseDto;
import click.ahimmoyak.studentservice.company.entity.Affiliation;
import click.ahimmoyak.studentservice.company.entity.Company;
import click.ahimmoyak.studentservice.company.repository.AffiliationRepository;
import click.ahimmoyak.studentservice.company.repository.CompanyRepository;
import click.ahimmoyak.studentservice.course.entity.Enrollment;
import click.ahimmoyak.studentservice.course.repository.CourseProvideRepository;
import click.ahimmoyak.studentservice.course.repository.CourseRepository;
import click.ahimmoyak.studentservice.course.repository.EnrollmentRepository;
import click.ahimmoyak.studentservice.global.dto.MessageResponseDto;
import click.ahimmoyak.studentservice.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Transactional
    public MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto requestDto) {
        User user = userService.getAuth(userDetails);

        if (user.getAffiliation()!= null) {
            String company = user.getAffiliation().getCompany().getName();
            throw new ApiException(HttpStatus.CONFLICT, "이미 소속된 회사가 존재합니다 : " + company);
        }

        if (companyRepository.existsByBusinessNumber(requestDto.businessNumber())) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 존재하는 사업자 번호입니다.");
        }

        Company company = companyRepository.save(requestDto.toEntity());

        Affiliation affiliation = Affiliation.builder()
                .company(company)
                .user(user)
                .isSupervisor(true)
                .build();
        affiliationRepository.save(affiliation);

        user.updateRole(UserRole.SUPERVISOR);
        userRepository.save(user);

        return MessageResponseDto.builder()
                .message("회사생성 완료")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchCompanyResponseDto> searchCompany(String name) {
        List<Company> companies = companyRepository.findByNameContaining(name);

        if (companies.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "해당하는 회사를 찾을 수 없습니다.");
        }

        return companies.stream()
                .map(SearchCompanyResponseDto::from)
                .collect(Collectors.toList());
    }

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
    public CheckCompanyResponseDto checkCompanyEmail(String companyEmail, String userEmail) {
        String companyDomain = extractDomain(companyEmail);
        String userDomain = extractDomain(userEmail);

        boolean isDomainMatch = companyDomain.equals(userDomain);
        return CheckCompanyResponseDto.builder()
                .message(isDomainMatch ? "도메인이 일치합니다." : "도메인이 일치하지 않습니다.")
                .success(isDomainMatch)
                .build();

    }

    private String extractDomain(String email) {
        String[] parts = email.split("@");

        if (parts.length == 2) {
            return parts[1].toLowerCase();
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다");
        }
    }


    @Override
    @Transactional
    public MessageResponseDto addAffiliation(UserDetailsImpl userDetails, String companyName) {
        User user = userService.getAuth(userDetails);

        if (user.getAffiliation() != null) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 Affiliation 이 존재합니다");
        }

        Affiliation affiliation = Affiliation.builder()
                .company(companyRepository.findByName(companyName)
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "해당하는 회사를 찾을수 없습니다.")))
                .user(userRepository.findById(user.getId())
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "해당하는 userId 를 찾을수 없습니다.")))
                .isSupervisor(false)
                .build();
        affiliationRepository.save(affiliation);

        user.updateRole(UserRole.EMPLOYEE);
        userRepository.save(user);

        return MessageResponseDto.builder()
                .message("affiliation 생성 완료")
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

        user.updateRole(UserRole.NORMAL);
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

        if (supervisor.getRole() != UserRole.SUPERVISOR) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "해당 사원을 삭제할 권한이 없습니다.");
        }

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

        user.updateRole(UserRole.NORMAL);

        return MessageResponseDto.builder()
                .message("사원 삭제 완료")
                .build();
    }

}
