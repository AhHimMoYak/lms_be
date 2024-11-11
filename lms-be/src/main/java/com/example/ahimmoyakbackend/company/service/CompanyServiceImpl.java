package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.course.repository.EnrollmentRepository;
import com.example.ahimmoyakbackend.global.dto.MessageResponseDto;
import com.example.ahimmoyakbackend.global.exception.ApiException;
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

    @Override
    @Transactional
    public MessageResponseDto createCompany(UserDetailsImpl userDetails, CreateCompanyRequestDto requestDto) {
        User user = userService.getAuth(userDetails);

        if (companyRepository.existsByBusinessNumber(requestDto.business_number())) {
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
    @Transactional
    public MessageResponseDto updateCompany(UserDetailsImpl userDetails, Long companyId, UpdateCompanyRequestDto requestDto) {
        User user = userService.getAuth(userDetails);
        if (user.getRole() != UserRole.SUPERVISOR) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "회사를 수정할 권한이 없습니다.");
        }
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "해당 회사를 찾을 수 없습니다."));
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
    public MessageResponseDto addAffiliation(UserDetailsImpl userDetails, Long companyId) {
        User user = userService.getAuth(userDetails);

        if (user.getAffiliation() != null) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 Affiliation 이 존재합니다");
        }

        Affiliation affiliation = Affiliation.builder()
                .company(companyRepository.findById(companyId)
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "해당하는 companyId 를 찾을수 없습니다.")))
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
            if(enrollment.getCourseProvide().getCompanyId().equals(user.getAffiliation().getCompany().getId())) {
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
    public MessageResponseDto deleteAffiliation(UserDetailsImpl userDetails, Long userId) {
        User supervisor = userService.getAuth(userDetails);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "해당 사원이 존재하지 않습니다.")
        );

        if (supervisor.getRole() != UserRole.SUPERVISOR) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "해당 사원을 삭제할 권한이 없습니다.");
        }

        Affiliation affiliation = affiliationRepository.findByUserId(userId).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "해당 계약이 존재하지 않습니다.")
        );

        if (enrollmentRepository.existsById(user.getId())) {
            Enrollment enrollment = enrollmentRepository.findById(user.getId()).orElseThrow(null);
            if (enrollment.getCourseProvide().getCompanyId().equals(user.getAffiliation().getCompany().getId())) {
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

    @Override
    @Transactional(readOnly = true)
    public List<GetEmployeeListResponseDto> getEmployeeList(UserDetailsImpl userDetails) {
        User supervisor = userService.getAuth(userDetails);
        if (supervisor.getRole() != UserRole.SUPERVISOR) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "해당 사원을 조회할 권한이 없습니다.");
        }
        Long companyId = supervisor.getAffiliation().getCompany().getId();

        if(companyId == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "해당 supervisor 의 companyId가 존재하지 않습니다");
        }

        List<Affiliation> employees = affiliationRepository.findByCompany_Id(companyId);

        return employees.stream()
                .map(GetEmployeeListResponseDto::from)
                .toList();
    }

    @Override
    public MessageResponseDto createCourseProvider(UserDetailsImpl userDetails) {
        return null;
    }

    @Override
    public List<CourseProvideListDto> getCourseProvideList(UserDetailsImpl userDetails) {
        return List.of();
    }

    @Override
    public MessageResponseDto submitEmployeeListForEnrollment(UserDetailsImpl userDetails, submitEmployeeListRequestDto requestDto) {
        return null;
    }

}
